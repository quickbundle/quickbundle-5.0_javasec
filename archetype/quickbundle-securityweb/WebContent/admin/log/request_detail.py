# -*- coding: utf-8 -*-
import sys
import re
import datetime
import math
import os
import traceback

from qb.tools.support.log import analyse_qbrm_log
from qb.tools.support.log import ui_public

from javax.servlet.http import HttpServlet
from org.quickbundle.config import RmConfig
from org.quickbundle.config import RmConfigVo

encode = RmConfig.getSingleton().getDefaultEncode()
warHome = RmConfig.getSingleton().getWarHome()
#log_file_encode = "gb18030"
log_file_encode='utf-8'
max_row = 100

class request_detail (HttpServlet):
    log_dic = {}
    log_para = None
    #custom log_para
    default_log_para = None
    url = None
    def doGet(self,request,response):
        self.doPost (request,response)

    def doPost(self,request,response):
        self.log_dic = {}
        sys.setrecursionlimit(sys.maxint)
        sb = []
        out = response.getWriter()
        self.log_para = request.getParameter("log")
        self.url = request.getParameter("url")
        result = ui_public.parse_log_para(request, response, self.log_para)
        fs = result[0]['request']
        date_from = result[1]
        date_to = result[2]
        date_from_ms= date_from*1000
        date_to_ms = date_to*1000
        start_time = datetime.datetime.now()
        uuids = self.find_uuids(fs, date_from_ms, date_to_ms)
        sqls = []
        methods = []
        if len(uuids) > 0:
            sqls = self.find_sqls(result[0]['sql'], date_from_ms, date_to_ms, uuids)
            methods = self.find_methods(result[0]['method'], date_from_ms, date_to_ms, uuids)
        
        cost = datetime.datetime.now() - start_time
        print 'request_detail ', request.getParameter("url"), ' cost, ', cost
        out = response.getWriter()
        self.appendTreeOut(uuids, sqls, methods, out)

    def appendTreeOut(self, uuids, sqls, methods, out):
        t = {}
        for s in sqls:
            info = s.split('|', 6)
            uuid_column = info[2].split('.', 4)
            unique_uuid = uuid_column[0]
            transaction_uuid = uuid_column[2] if len(uuid_column)<4 else uuid_column[3]
            if not t.has_key(unique_uuid):
                t[unique_uuid] = [{},[]]
            t_uuid = t[unique_uuid][0]
            if not t_uuid.has_key(transaction_uuid):
                t_uuid[transaction_uuid] = []
            t_uuid[transaction_uuid].append([info[0], info[1], info[5]])
        for s in methods:
            info = s.split('|', 6)
            uuid_column = info[3].split('.', 2)
            unique_uuid = uuid_column[0]
            if not t.has_key(unique_uuid):
                t[unique_uuid] = [{},[]]
            this_methods = t[unique_uuid][1]
            t[unique_uuid][1].append([info[1], info[2], info[4], info[5]])
        for dic1_key in t.keys():
            out.println('<b>' + uuids[dic1_key] + '|' + dic1_key + '</b>')
            dic1 = t[dic1_key][0]
            for dic2_key in dic1:
                array1 = dic1[dic2_key]
                sql_strs = []
                transaction_begin = -1
                transaction_end = -1
                for arr in array1:
                    ts = long(arr[0])
                    if transaction_begin < 0 or transaction_begin > (ts - long(arr[1])):
                        transaction_begin = ts - long(arr[1])
                    if transaction_end < 0 or transaction_begin < ts:
                        transaction_end = ts
                    sql_str = '';
                    sql_str += '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                    sql_str += arr[1]
                    sql_str += "|"
                    sql_str += arr[2]
                    sql_strs.append(sql_str)
                out.println('<br>&nbsp;&nbsp;&nbsp;&nbsp;' + str(transaction_end - transaction_begin) + '|' + dic2_key)
                for sql_str in sql_strs:
                    out.print(sql_str)
            method1 = t[dic1_key][1]
            if len(method1) > 0:
                out.println('<div class="rtop">')
            for meth in method1:
                out.println('&nbsp;&nbsp;&nbsp;&nbsp;' + meth[0] + '|' + meth[1] + '|' + meth[2] + '.' + meth[3] + '<br/>')
            if len(method1) > 0:
                out.println('</div>')
            out.println('<br/>')

    def getServletInfo(self):
        return "Short Description"

    def find_uuids(self, fs, date_from_ms, date_to_ms):
        uuids = {}
        for f in fs:
            lines = file(f).readlines()
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'readline ok'
            if not analyse_qbrm_log.is_effective_log_file(lines, date_from_ms, date_to_ms):
                print 'ignore ', f
                continue
            for line in lines:
                line = ui_public.get_line(line, log_file_encode)
                log_infos = line.split('|', 5)
                if len(log_infos) < 6:
                    continue
                epoch = (long)(log_infos[0])
                if date_from_ms > 0 and epoch < date_from_ms:
                    continue
                if date_to_ms > 0 and epoch > date_to_ms:
                    continue
                request_url = log_infos[4]
                if request_url.find('?') < 0 and len(log_infos[5]) > 0:
                    request_url += '?'
                request_url += log_infos[5]
                if request_url == self.url:
                    uuids[log_infos[3][0:log_infos[3].find('.')]] = log_infos[1][0:log_infos[1].find(',')]
                if len(uuids) > max_row:
                    break
            if len(uuids) > max_row:
                break
        return uuids
    
    def find_sqls(self, fs, date_from_ms, date_to_ms, uuids):
        sqls = []
        for f in fs:
            lines = file(f).readlines()
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'readline ok'
            if not analyse_qbrm_log.is_effective_log_file(lines, date_from_ms, date_to_ms):
                print 'ignore ', f
                continue
            for line in lines:
                line = ui_public.get_line(line, log_file_encode)
                for uuid_key in uuids.keys():
                    if line.find(uuid_key) > -1:
                        sqls.append(line)
        return sqls
        
    def find_methods(self, fs, date_from_ms, date_to_ms, uuids):
        methods = []
        for f in fs:
            lines = file(f).readlines()
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'readline ok'
            if not analyse_qbrm_log.is_effective_log_file(lines, date_from_ms, date_to_ms):
                print 'ignore ', f
                continue
            for line in lines:
                line = ui_public.get_line(line, log_file_encode)
                for uuid_key in uuids.keys():
                    if line.find(uuid_key) > -1:
                        methods.append(line)
        return methods
        
    def sort_dict_executeTime(self, sb, max_row, dic):
        sb.append( '<h4>按request的执行时间排序:</h4>')
        s_execute_time = sorted(dic.items(), cmp= lambda x,y:cmp(x,y), key=lambda dic: sum(dic[1][1])*1.0/len(dic[1][1]), reverse = True)
        index = 0
        for m in s_execute_time:
            if index >= max_row:
                break
            s = ''
            if index < focus_row_count:
                s += '<div class="focus">'
            s += '<span style="cursor:pointer;" onclick="request_onclick(\'' + m[1][0].encode(encode) + '\')">&#43; </span>'
            s += 'avg:' + "%.0f" %(sum(m[1][1])*1.0/len(m[1][1])) + 'ms | sql cost:' + "%.0f" %(sum(m[1][2])*1.0/len(m[1][2])) +  'ms | sql count: ' + "%.1f" %(sum(m[1][3])*1.0/len(m[1][1])) + ' | count:' + str(len(m[1][1])) + ' | rsd:' + "%.1f" % analyse_qbrm_log.get_rsd(m[1][1]) + ' | ' + m[1][0].encode(encode)
            if index < focus_row_count:
                s += '</div>'
            sb.append(s)
            index += 1
        sb.append( '<h4>按request的出现频率排序:</h4>')
        s_count = sorted(dic.items(), cmp= lambda x,y:cmp(x,y), key=lambda dic: len(dic[1][1]), reverse = True)
        index = 0
        for m in s_count:
            if index >= max_row:
                break
            s = ''
            if index < focus_row_count:
                s += '<div class="focus">'
            tmp_avg = sum(m[1][1])*1.0/len(m[1][1])
            tmp_avg_str = str("%.0f" %tmp_avg)
            if tmp_avg > 1000:
                tmp_avg_str = '<b>' + tmp_avg_str + '</b>'
            s += 'avg:' + tmp_avg_str + 'ms | sql cost:' + "%.0f" %(sum(m[1][2])*1.0/len(m[1][2])) +  'ms | sql count: ' + "%.1f" %(sum(m[1][3])*1.0/len(m[1][1])) + ' | count:' + str(len(m[1][1])) + ' | rsd:' + "%.1f" % analyse_qbrm_log.get_rsd(m[1][1]) + ' | ' + m[1][0].encode(encode)
            if index < focus_row_count:
                s += '</div>'
            sb.append(s)
            index += 1