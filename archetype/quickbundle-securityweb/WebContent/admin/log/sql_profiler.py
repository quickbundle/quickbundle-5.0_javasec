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
from org.quickbundle.third.jython import JythonSqlProfiler

encode = RmConfig.getSingleton().getDefaultEncode()
warHome = RmConfig.getSingleton().getWarHome()
#log_file_encode = "gb18030"
log_file_encode='utf-8'

pattern_sql_log = re.compile('''(\d+)\|([\d-]+)\|([\d-]+)\|s\|(.*)\|(.+)''')

#p_id = r"\d{19}|'\d{1,10}'" #通用
p_id = r"'%?[^'%]+%?'|\b\d+\b"

p_ids = r'''(@id\s*,?){2,}'''

max_row_count = 500
focus_row_count = 50

class sql_profiler (HttpServlet):
    valid_row_count = 0
    sql_log_dic = {}
    log_para = None
    #custom log_para
    default_log_para = None
    def doGet(self,request,response):
        self.doPost (request,response)

    def doPost(self,request,response):
        self.valid_row_count = 0
        self.sql_log_dic = {}
        self.log_para = None
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
        fs = result[0]['sql']
        date_from = result[1]
        date_to = result[2]
        date_from_ms= date_from*1000
        date_to_ms = date_to*1000
        start_time = datetime.datetime.now()
        if request.getParameter("cmd") == "executeTime":
            self.statisticSqlExecuteTime(fs, sb, date_from_ms, date_to_ms)
        elif request.getParameter("cmd") == "errorSql":
            self.statisticErrorSql(fs, sb, date_from_ms, date_to_ms)

        if request.getParameter("cmd") != None and len(request.getParameter("cmd")) > 0:
            cost = datetime.datetime.now() - start_time
            print request.getParameter("cmd"), p_id, ' cost, ', cost
            ui_public.print_result_head(request, response, fs, date_from, date_to, self.valid_row_count, cost)
        else:
            ui_public.print_query_form(request, response, self.log_para, [['executeTime', '统计SQL执行耗时'], ['errorSql', '统计报错SQL']])
        #输出结果
        ui_public.print_result_content_html(request, response, sb)

    def getServletInfo(self):
        return "Short Description"

    #统计执行错误的SQL
    def statisticErrorSql(self, fs, sb, date_from_ms, date_to_ms):
        sb.append( '\n<h4>执行错误的SQL:</h4>')
        error_sql_dic = {}
        for f in fs:
            lines = file(f).readlines()
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'readline ok'
            if not analyse_qbrm_log.is_effective_log_file(lines, date_from_ms, date_to_ms):
                print 'ignore ', f
                continue
            for line in lines:
                line = ui_public.get_line(line, log_file_encode)
                sql_log = line.split('|', 6)
                if len(sql_log) < 7 or sql_log[3] != 's':
                    continue
                epoch = (long)(sql_log[0])
                if date_from_ms > 0 and epoch < date_from_ms:
                    continue
                if date_to_ms > 0 and epoch > date_to_ms:
                    continue
                self.valid_row_count += 1
                key = sql_log[5] + sql_log[6]
                if error_sql_dic.__contains__(key):
                    error_sql_dic[key][2] = error_sql_dic[key][2] + 1
                else:
                    error_sql_dic[key] = [sql_log[5], sql_log[6], 1]
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'merge ok'
        for error_sql in error_sql_dic.items():
            sb.append('\n' + str(error_sql[1][2]) + '次, ' + error_sql[1][0] + ' ' + error_sql[1][1])
    
    #统计SQL执行时间
    def statisticSqlExecuteTime(self, fs, sb, date_from_ms, date_to_ms):
        for f in fs:
            sys.setrecursionlimit(sys.maxint)
            #1 begin read()读入，全部匹配            

            #2 begin 分行载入，逐条匹配
            
            #3 begin 全部载入，字符串分析
            lines = file(f).readlines()
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'readline ok'
            if not analyse_qbrm_log.is_effective_log_file(lines, date_from_ms, date_to_ms):
                print 'ignore ', f
                continue
            for line in lines:
                line = ui_public.get_line(line, log_file_encode)
                sql_log = line.split('|', 5)
                if len(sql_log) < 6 or sql_log[3] != 's':
                    continue
                epoch = (long)(sql_log[0])
                if date_from_ms > 0 and epoch < date_from_ms:
                    continue
                if date_to_ms > 0 and epoch > date_to_ms:
                    continue
                self.valid_row_count += 1
                strsql_merge = self.get_abstract_sql(sql_log[4], sql_log[5])
                if self.sql_log_dic.__contains__(strsql_merge):
                    sql_times = self.sql_log_dic[strsql_merge][1]
                    sql_times.append(int(sql_log[1]))
                else:
                    statement_type = ''
                    if len(sql_log[4]) == 0:
                        statement_type = 's'
                    self.sql_log_dic[strsql_merge] = [sql_log[5], [int(sql_log[1])], statement_type]
            #end
            
            print datetime.datetime.now().isoformat().replace('T', ' '), f, 'merge ok'
        self.sort_dict(sb, max_row_count, self.sql_log_dic)
        print datetime.datetime.now().isoformat().replace('T', ' '), fs, 'sort ok'
    
    def get_abstract_sql(self, prepared_sql, special_sql):
        if len(prepared_sql) > 0:
            if prepared_sql.find('?') > -1:
                return prepared_sql
            else:
                special_sql = prepared_sql
        try:
    #        strsql = re.sub(p_id, '@id', special_sql.upper())
    #        strsql = p_ids.sub('@ids', strsql)
            strsql = JythonSqlProfiler.getReplaceSql(special_sql, p_id, p_ids) #改为java的正则
            return strsql
        except:
            print traceback.format_exc()
        return special_sql
    
    def sort_dict(self, sb, max_row, dic):
        sb.append( '<h4>按SQL的平均执行时间排序:</h4>')
        s_execute_time = sorted(dic.items(), cmp= lambda x,y:cmp(x,y), key=lambda dic: sum(dic[1][1])*1.0/len(dic[1][1]), reverse = True)
        index = 0
        for m in s_execute_time:
            if index >= max_row:
                break
            s = ''
            if index < focus_row_count:
                s += '<div class="focus">'
            s += 'avg:' + "%.0f" %(sum(m[1][1])*1.0/len(m[1][1])) + 'ms | count:' + str(len(m[1][1])) + ' | rsd:' + "%.1f" % analyse_qbrm_log.get_rsd(m[1][1]) + ' | ' + m[1][2] + ' | ' + m[1][0].encode(encode).replace('<', '&lt;')
            if index < focus_row_count:
                s += '</div>'
            sb.append(s)
            index += 1
        sb.append( '<h4>按SQL的出现频率排序:</h4>')
        s_count = sorted(dic.items(), cmp= lambda x,y:cmp(x,y), key=lambda dic: len(dic[1][1]), reverse = True)
        index = 0
        for m in s_count:
            if index >= max_row:
                break
            s = ''
            if index < focus_row_count:
                s += '<div class="focus">'
            tmp_avg = sum(m[1][1])*1.0/len(m[1][1])
            tmp_avg_str = str("%.0f" %(sum(m[1][1])*1.0/len(m[1][1])))
            if tmp_avg > 50:
                tmp_avg_str = '<b>' + tmp_avg_str + '</b>'
            s += 'avg:' + tmp_avg_str + 'ms | count:' + str(len(m[1][1])) + ' | rsd:' + "%.1f" % analyse_qbrm_log.get_rsd(m[1][1]) + ' | ' + m[1][2] + ' | ' + m[1][0].encode(encode).replace('<', '&lt;')
            if index < focus_row_count:
                s += '</div>'
            sb.append(s)
            index += 1