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

max_row_count = 500
focus_row_count = 50

class request_profiler (HttpServlet):
    valid_row_count = 0
    log_dic = {}
    log_para = None
    #custom log_para
    default_log_para = None
    def doGet(self,request,response):
        self.doPost (request,response)

    def doPost(self,request,response):
        self.valid_row_count = 0
        self.log_dic = {}
        sys.setrecursionlimit(sys.maxint)
        sb = []
        out = response.getWriter()
        
        self.log_para = request.getParameter("log")
        if self.log_para == None:
            self.log_para = self.default_log_para
        if self.log_para == None:
            self.log_para = '[request]\n' + warHome + '/logs/request.log*\n\n[sql]\n' + warHome + '/logs/sql.log*\n\n[method]\n' + warHome + '/logs/method.log*'
            
        ui_public.print_query_head(request, response);
        result = ui_public.parse_log_para(request, response, self.log_para)
        fs = result[0]['request']
        date_from = result[1]
        date_to = result[2]
        date_from_ms= date_from*1000
        date_to_ms = date_to*1000
        start_time = datetime.datetime.now()
        if request.getParameter("cmd") == "executeTime":
            self.statisticRequestExecuteTime(fs, sb, date_from_ms, date_to_ms, request.getParameter("match_user"), request.getParameter("match_url"))
        elif request.getParameter("cmd") == "sqlCount":
            self.statisticSqlCount(fs, sb, date_from_ms, date_to_ms, request.getParameter("match_user"), request.getParameter("match_url"))

        if request.getParameter("cmd") != None and len(request.getParameter("cmd")) > 0:
            cost = datetime.datetime.now() - start_time
            print request.getParameter("cmd"), ' cost, ', cost
            ui_public.print_result_head(request, response, fs, date_from, date_to, self.valid_row_count, cost)
        else:
            ext_code = '''<div style="padding:3px;">操作人（支持正则）：<input type="text" size="50" name="match_user" value=""></div>
<div style="padding:3px;">URL（支持正则）：<input type="text" size="50" name="match_url" value=""></div>'''
            ui_public.print_query_form(request, response, self.log_para, [['executeTime', '统计request耗时'], ['sqlCount', '统计request执行的SQL数']], ext_code)
        #输出结果
        ui_public.print_result_content_html(request, response, sb)

    def getServletInfo(self):
        return "Short Description"

    def do_executeRequest(self, fs, sb, date_from_ms, date_to_ms, match_user, match_url):
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
                if match_user != None and match_user != "":
                    uuid_thread_user = log_infos[3].split('.', 3)
                    if len(uuid_thread_user) < 3  or not(re.match(match_user, uuid_thread_user[2])):
                        continue
                epoch = (long)(log_infos[0])
                if date_from_ms > 0 and epoch < date_from_ms:
                    continue
                if date_to_ms > 0 and epoch > date_to_ms:
                    continue
                self.valid_row_count += 1
                request_url = log_infos[4]            
                total_time = (long)(log_infos[1][0:log_infos[1].index(',')])
                sql_time = (long)(log_infos[1][log_infos[1].index(',') + 1:])
                #sql count
                sql_counts = log_infos[2]
                sql_count_single = sql_counts[0 : sql_counts.index(',')]
                sql_counts = sql_counts[sql_counts.index(',')+1 : ]
                sql_count_batch_sum = sql_counts[sql_counts.index(',')+1 : ]
                total_sql_count = long(sql_count_single) + long(sql_count_batch_sum)
                if self.log_dic.__contains__(request_url):
                    self.log_dic[request_url][1].append(total_time)
                    self.log_dic[request_url][2].append(sql_time)
                    self.log_dic[request_url][3].append(total_sql_count)
                else:
                    request_uri = request_url
                    if request_url.find('?') < 0 and len(log_infos[5]) > 0:
                        request_uri += '?'
                    request_uri += log_infos[5]
                    self.log_dic[request_url] = [request_uri, [total_time], [sql_time], [total_sql_count]]
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'merge ok'
    
    #统计request执行时间
    def statisticRequestExecuteTime(self, fs, sb, date_from_ms, date_to_ms, match_user, match_url):
        self.do_executeRequest(fs, sb, date_from_ms, date_to_ms, match_user, match_url)
        self.sort_dict_executeTime(sb, max_row_count, self.log_dic)
        print datetime.datetime.now().isoformat().replace('T', ' '), fs, 'sort ok'
        
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
            s += '<span style="cursor:pointer;" onclick="request_onclick(this, \'' + m[1][0].encode(encode) + '\')">&#43;</span>'
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
    
    #统计request执行时间
    def statisticSqlCount(self, fs, sb, date_from_ms, date_to_ms, match_user, match_url):
        self.do_executeRequest(fs, sb, date_from_ms, date_to_ms, match_user, match_url)
        self.sort_dict_sqlCount(sb, max_row_count, self.log_dic)
        print datetime.datetime.now().isoformat().replace('T', ' '), fs, 'sort ok'
        
    def sort_dict_sqlCount(self, sb, max_row, dic):
        sb.append( '<h4>按request执行的SQL数量排序:</h4>')
        s_execute_time = sorted(dic.items(), cmp= lambda x,y:cmp(x,y), key=lambda dic: sum(dic[1][3])*1.0/len(dic[1][3]), reverse = True)
        index = 0
        for m in s_execute_time:
            if index >= max_row:
                break
            s = ''
            if index < focus_row_count:
                s += '<div class="focus">'
            s += '<span style="cursor:pointer;" onclick="request_onclick(this, \'' + m[1][0].encode(encode) + '\')">&#43;</span>'
            s += 'avg:' + "%.1f" %(sum(m[1][3])*1.0/len(m[1][1])) + ' | sql cost:' + "%.0f" %(sum(m[1][2])*1.0/len(m[1][2])) + 'ms | request cost:' + "%.0f" %(sum(m[1][1])*1.0/len(m[1][1])) +  'ms | count:' + str(len(m[1][1])) + ' | rsd:' + "%.1f" % analyse_qbrm_log.get_rsd(m[1][1]) + ' | ' + m[1][0].encode(encode)
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
            tmp_avg = sum(m[1][3])*1.0/len(m[1][3])
            tmp_avg_str = str("%.1f" %tmp_avg)
            if tmp_avg > 1000:
                tmp_avg_str = '<b>' + tmp_avg_str + '</b>'
            s += 'avg:' + tmp_avg_str + ' | sql cost:' + "%.0f" %(sum(m[1][2])*1.0/len(m[1][2])) + 'ms | request cost:' + "%.0f" %(sum(m[1][1])*1.0/len(m[1][1])) +  'ms | count:' + str(len(m[1][1])) + ' | rsd:' + "%.1f" % analyse_qbrm_log.get_rsd(m[1][1]) + ' | ' + m[1][0].encode(encode)
            if index < focus_row_count:
                s += '</div>'
            sb.append(s)
            index += 1