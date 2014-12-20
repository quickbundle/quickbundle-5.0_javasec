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

class method_profiler (HttpServlet):
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
        fs = result[0]['method']
        date_from = result[1]
        date_to = result[2]
        date_from_ms= date_from*1000
        date_to_ms = date_to*1000
        start_time = datetime.datetime.now()
        if request.getParameter("cmd") == "executeTime":
            self.statisticMethodExecuteTime(fs, sb, date_from_ms, date_to_ms)

        if request.getParameter('cmd') != None and len(request.getParameter('cmd')) > 0:
            cost = datetime.datetime.now() - start_time
            print request.getParameter('cmd'), ' cost, ', cost
            ui_public.print_result_head(request, response, fs, date_from, date_to, self.valid_row_count, cost)
        else:
            ui_public.print_query_form(request, response, self.log_para, [['executeTime', '统计method耗时']])
        #输出结果
        ui_public.print_result_content_html(request, response, sb)

    def getServletInfo(self):
        return "Short Description"

    def do_executeMethod(self, fs, sb, date_from_ms, date_to_ms):
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
                self.valid_row_count += 1
                class_method = log_infos[4] + '.' + log_infos[5]
                execute_time = long(log_infos[1])
                return_size = 0
                if len(log_infos[2]) > 0:
                    return_size = long(log_infos[2])
                if self.log_dic.__contains__(class_method):
                    self.log_dic[class_method][1].append(execute_time)
                    self.log_dic[class_method][2].append(return_size)
                else:
                    self.log_dic[class_method] = [class_method, [execute_time], [return_size]]
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'merge ok'
    
    #统计request执行时间
    def statisticMethodExecuteTime(self, fs, sb, date_from_ms, date_to_ms):
        self.do_executeMethod(fs, sb, date_from_ms, date_to_ms)
        self.sort_dict_executeTime(sb, max_row_count, self.log_dic)
        print datetime.datetime.now().isoformat().replace('T', ' '), fs, 'sort ok'
        
    def sort_dict_executeTime(self, sb, max_row, dic):
        sb.append( '<h4>按method的执行时间排序:</h4>')
        s_execute_time = sorted(dic.items(), cmp= lambda x,y:cmp(x,y), key=lambda dic: sum(dic[1][1])*1.0/len(dic[1][1]), reverse = True)
        index = 0
        for m in s_execute_time:
            if index >= max_row:
                break
            s = ''
            if index < focus_row_count:
                s += '<div class="focus">'
            focus_size = sum(m[1][2])*1.0/len(m[1][2])
            focus_size_str = str("%.0f" %focus_size)
            if focus_size > 50:
                focus_size_str = '<b>' + focus_size_str + '</b>'
            s += 'avg:' + "%.0f" %(sum(m[1][1])*1.0/len(m[1][1])) + 'ms | return size:' + focus_size_str + ' | count:' + str(len(m[1][1])) + ' | rsd:' + "%.1f" % analyse_qbrm_log.get_rsd(m[1][1]) + ' | ' + m[1][0].encode(encode)
            if index < focus_row_count:
                s += '</div>'
            sb.append(s)
            index += 1
        sb.append( '<h4>按method的出现频率排序:</h4>')
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
            if tmp_avg > 500:
                tmp_avg_str = '<b>' + tmp_avg_str + '</b>'
            focus_size = sum(m[1][2])*1.0/len(m[1][2])
            focus_size_str = str("%.0f" %focus_size)
            if focus_size > 50:
                focus_size_str = '<b>' + focus_size_str + '</b>'
            s += 'avg:' + tmp_avg_str + 'ms | return size:' + focus_size_str + ' | count:' + str(len(m[1][1])) + ' | rsd:' + "%.1f" % analyse_qbrm_log.get_rsd(m[1][1]) + ' | ' + m[1][0].encode(encode)
            if index < focus_row_count:
                s += '</div>'
            sb.append(s)
            index += 1