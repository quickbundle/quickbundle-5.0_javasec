var isIE=(navigator.userAgent.toLowerCase().indexOf("msie")!=-1);
if(!isIE){ //firefox innerText define
    HTMLElement.prototype.__defineGetter__("innerText", 
    function(){
        var anyString = "";
        var childS = this.childNodes;
        for(var i=0; i<childS.length; i++) { 
            if(childS[i].nodeType==1)
                //anyString += childS[i].tagName=="BR" ? "\n" : childS[i].innerText;
                anyString += childS[i].innerText;
            else if(childS[i].nodeType==3)
                anyString += childS[i].nodeValue;
        }
        return anyString;
    } 
    ); 
    HTMLElement.prototype.__defineSetter__("innerText", 
    function(sText){
        this.textContent=sText; 
    } 
    ); 
}
jQuery(function() {
	var uniqueID = "123456789";
	var eleHtml = '<table border=0 cellpadding=0 cellspacing=0  background="images/day_title.gif" align=center class=WholeCalendar_' + uniqueID + '> ' +
	  '  <tr style="border-bottom-width:1px; border-bottom-style:solid; border-bottom-color:black;">                                          ' +
	  '      <td class=Title_' + uniqueID + '  ></td>  ' +
	  '      <td class=DateControls_' + uniqueID + ' > ' +
	  '        <nobr> <select></select>                ' +
	  '               <select></select>                ' +
	  '               <input type=button id=button1 name=button1  style="border-width:0px;height:22" value="确定" class=enter onclick="return button1_onclick()"></input><input type=button name=button2  style="border-width:0px;height:22" value="清空" class=enter onclick="return button2_onclick()"></input>'+
	  ' </nobr> </td>  ' +
	  '  </tr>                                         ' +
	  '  <tr> <td colspan=3>                           ' +
	  '    <table align=center valign=center class=CalTable_' + uniqueID + ' cellspacing=0 border=0> ' +
	  '      <tr><td style="BORDER-TOP: 1px solid DimGray " class=DayTitle_' + uniqueID + '></td>' +
	  '          <td style="BORDER-TOP: 1px solid DimGray " class=DayTitle_' + uniqueID + '></td>' +
	  '          <td style="BORDER-TOP: 1px solid DimGray " class=DayTitle_' + uniqueID + '></td>' +
	  '          <td style="BORDER-TOP: 1px solid DimGray " class=DayTitle_' + uniqueID + '></td>' +
	  '          <td style="BORDER-TOP: 1px solid DimGray " class=DayTitle_' + uniqueID + '></td>' +
	  '          <td style="BORDER-TOP: 1px solid DimGray " class=DayTitle_' + uniqueID + '></td>' +
	  '          <td style="BORDER-TOP: 1px solid DimGray " class=DayTitle_' + uniqueID + '></td></tr>' +
	  '      <tr ><td height="23"></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' +
	  '      <tr ><td height="23"></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' +
	  '      <tr ><td height="23"></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' +
	  '      <tr ><td height="23"></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' +
	  '      <tr ><td height="23"></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' +
	  '      <tr ><td height="23"></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' +
	  '    </table> ' +
	  '  </tr>      ' +
	  '</table>     ';
	var element = null;
	var gaMonthNames = new Array(
			new Array('一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'), 
			new Array('一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月')
	);
	var gaDayNames = new Array(
			new Array('S', 'M', 'T', 'W', 'T', 'F', 'S'),
			new Array('日', '一', '二', '三', '四', '五', '六'), 
			new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday')
	);
	var gaMonthDays = new Array(
	/* Jan */31, /* Feb */29, /* Mar */31, /* Apr */30,
	/* May */31, /* Jun */30, /* Jul */31, /* Aug */31,
	/* Sep */30, /* Oct */31, /* Nov */30, /* Dec */31);
	var StyleInfo = null;
	var goStyle = new Object();
	var gaDayCell = new Array()
	var goDayTitleRow = null
	var goYearSelect = null
	var goMonthSelect = null
	var goCurrentDayCell = null
	var giStartDayIndex = 0
	var gbLoading = true
	var giDay
	var giMonth
	var giYear
	var giMonthLength = 1
	var giDayLength = 1
	var giFirstDay = 0
	var gsGridCellEffect = 'raised'
	var gsGridLinesColor = 'black'
	var gbShowDateSelectors = true
	var gbShowDays = true
	var gbShowTitle = true
	var gbShowHorizontalGrid = true
	var gbShowVerticalGrid = true
	var gbValueIsNull = false
	var gbReadOnly = false
	var giMinYear = 1900
	var giMaxYear = 2099
	function fnGetDay() {
		return (gbValueIsNull) ? null : giDay
	}
	function fnPutDay(iDay) {
		if (gbLoading)
			return // return if the behavior is loading
			iDay = parseInt(iDay)
		if (isNaN(iDay))
			throw "日期参照可能不正常，请再试。"
		fnSetDate(iDay, giMonth, giYear)
	}
	function fnGetMonth() {
		return (gbValueIsNull) ? null : giMonth
	}
	function fnPutMonth(iMonth) {
		if (gbLoading)
			return // return if the behavior is loading
			iMonth = parseInt(iMonth)
		if (isNaN(iMonth))
			throw "日期参照可能不正常，请再试。"
		fnSetDate(giDay, iMonth, giYear)
	}
	function fnGetYear() {
		return (gbValueIsNull) ? null : giYear
	}
	function fnPutYear(iYear) {
		if (gbLoading)
			return // return if the behavior is loading
			iYear = parseInt(iYear)
		if (isNaN(iYear))
			throw "日期参照可能不正常，请再试。"
		fnSetDate(giDay, giMonth, iYear)
	}
	function fnGetMonthLength() {
		if (giMonthLength == 0)
			return "short"
		if (giMonthLength == 1)
			return "long"
	}
	function fnPutMonthLength(sLength) {
		if (gbLoading)
			return 

		switch (sLength.toLowerCase()) {
		case "short":
			if (giMonthLength == 0)
				return

			giMonthLength = 0
			break;
		case "long":
			if (giMonthLength == 1)
				return

			giMonthLength = 1
			break;
		default:
			throw "日期参照可能不正常，请再试。"
			return

		}
		fnUpdateTitle()
		fnBuildMonthSelect()
	}
	function fnGetDayLength() {
		if (giDayLength == 0)
			return "short"
		if (giDayLength == 1)
			return "medium"
		if (giDayLength == 2)
			return "long"
	}
	function fnPutDayLength(sLength) {
		if (gbLoading)
			return

		switch (sLength.toLowerCase()) {
		case "short":
			if (giDayLength == 0)
				return

			giDayLength = 0
			break;
		case "medium":
			if (giDayLength == 1)
				return

			giDayLength = 1
			break;
		case "long":
			if (giDayLength == 2)
				return

			giDayLength = 2
			break;
		default:
			throw "日期参照可能不正常，请再试。"
			return

		}
		fnUpdateDayTitles()
		goStyle['DaySelected'].borderStyle = 'solid'
	}
	function fnGetFirstDay() {
		return giFirstDay
	}
	function fnPutFirstDay(iFirstDay) {
		if (gbLoading)
			return // return if the behavior is loading
		if ((iFirstDay < 0) || (iFirstDay > 6))
			throw "日期参照可能不正常，请再试。"
		if (giFirstDay == iFirstDay)
			return

		giFirstDay = iFirstDay
		fnUpdateDayTitles()
		fnFillInCells()
	}
	function fnGetGridCellEffect() {
		return gsGridCellEffect
	}
	function fnPutGridCellEffect(sEffect) {
		if (gbLoading)
			return

		switch (sEffect.toLowerCase()) {
		case "raised":
			if (gsGridCellEffect == 'raised')
				return

			gsGridCellEffect = 'raised'
			fnUpdateGridColors()
			break
		case "flat":
			if (gsGridCellEffect == 'flat')
				return

			gsGridCellEffect = 'flat'
			fnUpdateGridColors()
			break
		case "sunken":
			if (gsGridCellEffect == 'sunken')
				return

			gsGridCellEffect = 'sunken'
			fnUpdateGridColors()
			break
		default:
			throw "日期参照可能不正常，请再试。"
		}
	}
	function fnGetGridLinesColor() {
		return gsGridLinesColor
	}
	function fnPutGridLinesColor(sGridLinesColor) {
		if (gbLoading)
			return

		gsGridLinesColor = sGridLinesColor
		fnUpdateGridColors()
	}
	function fnGetShowVerticalGrid() {
		return gbShowVerticalGrid
	}
	function fnPutShowVerticalGrid(bShowVerticalGrid) {
		if (gbLoading)
			return

		if ((bShowVerticalGrid) != gbShowVerticalGrid) {
			gbShowVerticalGrid = (bShowVerticalGrid) ? true : false
			fnFireOnPropertyChange("propertyName", "showVerticalGrid")
			fnUpdateGridColors()
		}
	}
	function fnGetShowHorizontalGrid() {
		return gbShowHorizontalGrid
	}
	function fnPutShowHorizontalGrid(bShowHorizontalGrid) {
		if (gbLoading)
			return

		if ((bShowHorizontalGrid) != gbShowHorizontalGrid) {
			gbShowHorizontalGrid = (bShowHorizontalGrid) ? true : false
			fnFireOnPropertyChange("propertyName", "showHorizontalGrid")
			fnUpdateGridColors()
		}
	}
	function fnGetShowDateSelectors() {
		return gbShowDateSelectors
	}
	function fnPutShowDateSelectors(bShowDateSelectors) {
		if (gbLoading)
			return

		gbShowDateSelectors = (bShowDateSelectors) ? true : false
		element.children[0].rows[0].cells[1].style.display = (gbShowDateSelectors) ? '' : 'none'
		element.children[0].rows[0].style.display = (gbShowDateSelectors || gbShowTitle) ? '' : 'none'
	}
	function fnGetShowDays() {
		return gbShowDays;
	}
	function fnPutShowDays(bShowDays) {
		if (gbLoading)
			return;

		gbShowDays = (bShowDays) ? true : false;
		goDayTitleRow.style.display = (gbShowDays) ? '' : 'none';
	}
	function fnGetShowTitle() {
		return gbShowTitle;
	}
	function fnPutShowTitle(bShowTitle) {
		if (gbLoading)
			return;

		gbShowTitle = (bShowTitle) ? true : false;
		element.children[0].rows[0].style.display = (gbShowDateSelectors || gbShowTitle) ? '' : 'none';
		fnUpdateTitle();
	}
	function fnGetValue() {
		var sValue;
		var sTmp;
		if (gbValueIsNull)
			return null;
		if (giYear < 10)
			sTmp = '000' + giYear;
		if (giYear < 100)
			sTmp = '00' + giYear;
		if (giYear < 1000)
			sTmp = '0' + giYear;
		sTmp = giYear
		sValue = sTmp + '-' + ((giMonth < 10) ? '0' + giMonth : giMonth) + '-' + ((giDay < 10) ? '0' + giDay : giDay)
		return sValue
	}
	function fnPutValue(sValue) {
		if (gbLoading)
			return 

		var aValue = sValue.split('-')
		aValue[0]++;
		aValue[0]--;
		aValue[1]++;
		aValue[1]--;
		aValue[2]++;
		aValue[2]--;
		if (isNaN(aValue[0]) || isNaN(aValue[1]) || isNaN(aValue[2]))
			throw "日期参照可能不正常，请再试。"
		fnSetDate(aValue[2], aValue[1], aValue[0])
	}
	function fnGetValueIsNull() {
		return gbValueIsNull
	}

	function fnPutValueIsNull(bValueIsNull) {
		if (gbLoading)
			return  

		if ((bValueIsNull) != gbValueIsNull) {
			gbValueIsNull = (bValueIsNull) ? true : false
			fnFireOnPropertyChange("propertyName", "readOnly")
		}

		goCurrentDayCell.className = (bValueIsNull) ? 'Day_' + uniqueID : 'DaySelected_' + uniqueID
	}

	function fnGetReadOnly() {
		return (gbReadOnly) ? true : false
	}
	function fnPutReadOnly(bReadOnly) {
		if (gbLoading)
			return  

		if ((bReadOnly) != gbReadOnly) {
			gbReadOnly = (bReadOnly) ? true : false
			fnFireOnPropertyChange("propertyName", "readOnly")
		}

		element.children[0].rows[0].cells[1].children[0].children[0].disabled = gbReadOnly
		element.children[0].rows[0].cells[1].children[0].children[1].disabled = gbReadOnly
	}
	function fnCreateCalendarHTML()
	{
	  var row, cell;
	  element.innerHTML = eleHtml;

	  goDayTitleRow = element.children[0].rows[1].cells[0].children[0].rows[0]
	  goMonthSelect = element.children[0].rows[0].cells[1].children[0].children[1]
	  goYearSelect = element.children[0].rows[0].cells[1].children[0].children[0]

	  for (row=1; row < 7; row++)
	    for (cell=0; cell < 7; cell++)
	      gaDayCell[((row-1)*7) + cell] = element.children[0].rows[1].cells[0].children[0].rows[row].cells[cell]

	}
	function addRule(s,l,c){
		if(!s)
			return false;
		if(s.insertRule){
			s.insertRule(l+"{"+c+"}",s.cssRules.length);
			return true;
		} else if(s.addRule){
			return s.addRule(l,c) ? true : false;
		}
		return false;
	}
	function addRule(sheet, selector, rule) {
		if(isIE) {
			sheet.addRule(selector, rule)
		} else {
			sheet.insertRule("" + selector + " { " + rule + " }", sheet.cssRules.length);
		}
	}
	function fnCreateStyleSheets()
	{
	  var StyleInfo
	  if(!document.styleSheets.length){
		  document.createStyleSheet();
	  }
	  StyleInfo = document.styleSheets[0];
	  addRule(StyleInfo, '.enter',
		  
	      'background-image: url(images/day_enter.gif);'+
	      'background-attachment: fixed;'+
	      'background-repeat: no-repeat;'+
	      'background-position: center center;'+
	      'text-align: center;'+
	      'vertical-align: middle;'+
	      'font-family: "Arial";'+
	      'color: #333333;'+
	      'font-size: 9px;'+
	      'text-decoration: none;'+
	      'border           : 0px solid black  ;'+
	      'cursor           : default          ;'+
	      'width            : 50px             ;'+
	      'height           : 30px             ;'
	  )
	    
	  addRule(StyleInfo, '.WholeCalendar_' + uniqueID,
	      'background-color : #e1e4e1        ;'+
	      'border           : 1px solid black  ;'+
	      'cursor           : default          ;'+
	      'width            : 100%             ;'+
	      'height           : 100%             ;'
	    )
	  if(isIE) {
		  goStyle['WholeCalendar'] = StyleInfo.rules[StyleInfo.rules.length - 1].style
	  } else {
		  goStyle['WholeCalendar'] = StyleInfo.cssRules[StyleInfo.cssRules.length - 1].style
	  }
	  addRule(StyleInfo,   '.Title_' + uniqueID,
		  
	      'color            : #000000  ;'+	// cal--title-color 
	      'font-family      : Arial    ;'+	// cal--title-font-family 
	      'font-size        : 9pt     ;'+	// cal--title-font-size 
	      'font-weight      : bold     ;'+	// cal--title-font-size 
	      'text-align       : center   ;'+	// cal--title-text-align 
	      'text-decoration  : none;' 

	    )
	  if(isIE) {
		  goStyle['Title'] = StyleInfo.rules[StyleInfo.rules.length - 1].style
	  } else {
		  goStyle['Title'] = StyleInfo.cssRules[StyleInfo.cssRules.length - 1].style
	  }
	  fnLoadCSSDefault('cal--title-background-color', 'calTitleBackgroundColor', goStyle['Title'], 'backgroundColor')
	  fnLoadCSSDefault('cal--title-color',            'calTitleColor',           goStyle['Title'], 'color')
	  fnLoadCSSDefault('cal--title-font-family',      'calTitleFontFamily',      goStyle['Title'], 'fontFamily')
	  fnLoadCSSDefault('cal--title-font-size',        'calTitleFontSize',        goStyle['Title'], 'fontSize')
	  fnLoadCSSDefault('cal--title-font-weight',      'calTitleFontWeight',      goStyle['Title'], 'fontWeight')
	  fnLoadCSSDefault('cal--title-text-align',       'calTitleTextAlign',       goStyle['Title'], 'textAlign')
	  addRule(StyleInfo,   '.DateControls_' + uniqueID,
	      'text-align : right ;'
	    )
	  if(isIE) {
		  goStyle['DateControls'] = StyleInfo.rules[StyleInfo.rules.length - 1].style
	  } else {
		  goStyle['DateControls'] = StyleInfo.cssRules[StyleInfo.cssRules.length - 1].style
	  }
	  addRule(StyleInfo,   '.CalTable_' + uniqueID,
	      'border : 0 solid black ;'+
	      'width  : 100%          ;'+
	      'height : 100%          ;'
	    )
	  if(isIE) {
		  goStyle['CalTable'] = StyleInfo.rules[StyleInfo.rules.length - 1].style
	  } else {
		  goStyle['CalTable'] = StyleInfo.cssRules[StyleInfo.cssRules.length - 1].style
	  }
		
	  addRule(StyleInfo,   '.DayTitle_' + uniqueID,
	      'background-color    : #87BAEA ;'+	 
	      'color               : #FFFFFF;     '+	 
	      'font-family         : Arial     ;'+	 
	      'font-size           : 10pt       ;'+	 
	      'font-weight         : bold      ;'+	 
	      'text-align          : center    ;'+
	      'vertical-align: middle;'+	 
	      'border-top-width: 0px      ;'+
	      'border-right-width: 0px     ;'+
	      'border-bottom-width: 1px      ;'+
	      'border-left-width: 0px     ;'+      
	      'border-top-style: none;'+
	      'border-right-style: none;'+
	      'border-bottom-style: solid;'+
	      'border-left-style: none;'+
	      'border-bottom-color: #FFFFFF;'
	    )
	  if(isIE) {
		  goStyle['DayTitle'] = StyleInfo.rules[StyleInfo.rules.length - 1].style
	  } else {
		  goStyle['DayTitle'] = StyleInfo.cssRules[StyleInfo.cssRules.length - 1].style
	  }
	  fnLoadCSSDefault('cal--dayTitle-background-color', 'calDayTitleBackgroundColor', goStyle['DayTitle'], 'backgroundColor')
	  fnLoadCSSDefault('cal--dayTitle-color',            'calDayTitleColor',           goStyle['DayTitle'], 'color')
	  fnLoadCSSDefault('cal--dayTitle-font-family',      'calDayTitleFontFamily',      goStyle['DayTitle'], 'fontFamily')
	  fnLoadCSSDefault('cal--dayTitle-font-size',        'calDayTitleFontSize',        goStyle['DayTitle'], 'fontSize')
	  fnLoadCSSDefault('cal--dayTitle-font-weight',      'calDayTitleFontWeight',      goStyle['DayTitle'], 'fontWeight')
	  fnLoadCSSDefault('cal--dayTitle-text-align',       'calDayTitleTextAlign',       goStyle['DayTitle'], 'textAlign')
	  addRule(StyleInfo,   '.OffDay_' + uniqueID,

	      'font-family: "Arial";'+	// cal--offMonth-background-color 
	      'font-size: 9px;'+	// cal--offMonth-color 
	      'color: #8AB9EC;'+	// cal--offMonth-font-family
	      'text-decoration: none;'+	// cal--offMonth-font-size 
	      'background-color: #EDF8FF;'+	// cal--offMonth-font-weight 
	      'border-top-width: 0px;'+	// cal--offMonth-text-align 
	      'border-right-width: 0px;'+	// cal--offMonth-vertical-align 
	      'border-bottom-width: 1px;'+
	      'border-left-width: 0px;'+
	      'border-top-style: none;'+
	      'border-right-style: none;'+
	      'border-bottom-style: solid;'+
	      'border-left-style: none;'+
	      'border-bottom-color: #FFFFFF;'+
	      'text-align: center;'+
	      'vertical-align: middle;'
	    )
	  if(isIE) {
		  goStyle['OffDay'] = StyleInfo.rules[StyleInfo.rules.length - 1].style
	  } else {
		  goStyle['OffDay'] = StyleInfo.cssRules[StyleInfo.cssRules.length - 1].style
	  }
	  fnLoadCSSDefault('cal--offMonth-background-color', 'calOffMonthBackgroundColor', goStyle['OffDay'], 'backgroundColor')
	  fnLoadCSSDefault('cal--offMonth-color',            'calOffMonthColor',           goStyle['OffDay'], 'color')
	  fnLoadCSSDefault('cal--offMonth-font-family',      'calOffMonthFontFamily',      goStyle['OffDay'], 'fontFamily')
	  fnLoadCSSDefault('cal--offMonth-font-size',        'calOffMonthFontSize',        goStyle['OffDay'], 'fontSize')
	  fnLoadCSSDefault('cal--offMonth-font-weight',      'calOffMonthFontWeight',      goStyle['OffDay'], 'fontWeight')
	  fnLoadCSSDefault('cal--offMonth-text-align',       'calOffMonthTextAlign',       goStyle['OffDay'], 'textAlign')
	  fnLoadCSSDefault('cal--offMonth-vertical-align',   'calOffMonthVerticalAlign',   goStyle['OffDay'], 'verticalAlign')
	  addRule(StyleInfo,   '.Day_' + uniqueID,
	      'font-family: "Arial";'+	// cal--offMonth-background-color 
	      'font-size: 10px;'+	// cal--offMonth-color 
	      'color: #002B5F;'+	// cal--offMonth-font-family
	      'text-decoration: none;'+	// cal--offMonth-font-size 
	      'background-color: #D9E9FA;'+	// cal--offMonth-font-weight 
	      'border-top-width: 0px;'+	// cal--offMonth-text-align 
	      'border-right-width: 0px;'+	// cal--offMonth-vertical-align 
	      'border-bottom-width: 1px;'+
	      'border-left-width: 0px;'+
	      'border-top-style: none;'+
	      'border-right-style: none;'+
	      'border-bottom-style: solid;'+
	      'border-left-style: none;'+
	      'border-bottom-color: #FFFFFF;'+
	      'text-align: center;'+
	      'vertical-align: middle;'
	    )
	  if(isIE) {
		  goStyle['Day'] = StyleInfo.rules[StyleInfo.rules.length - 1].style
	  } else {
		  goStyle['Day'] = StyleInfo.cssRules[StyleInfo.cssRules.length - 1].style
	  }
	  fnLoadCSSDefault('cal--currentMonth-background-color', 'calCurrentMonthBackgroundColor', goStyle['Day'], 'backgroundColor')
	  fnLoadCSSDefault('cal--currentMonth-color',            'calCurrentMonthColor',           goStyle['Day'], 'color')
	  fnLoadCSSDefault('cal--currentMonth-font-family',      'calCurrentMonthFontFamily',      goStyle['Day'], 'fontFamily')
	  fnLoadCSSDefault('cal--currentMonth-font-size',        'calCurrentMonthFontSize',        goStyle['Day'], 'fontSize')
	  fnLoadCSSDefault('cal--currentMonth-font-weight',      'calCurrentMonthFontWeight',      goStyle['Day'], 'fontWeight')
	  fnLoadCSSDefault('cal--currentMonth-text-align',       'calCurrentMonthTextAlign',       goStyle['Day'], 'textAlign')
	  fnLoadCSSDefault('cal--currentMonth-vertical-align',   'calCurrentMonthVerticalAlign',   goStyle['Day'], 'verticalAlign')
	  addRule(StyleInfo,    '.DaySelected_' + uniqueID,
	  
		
	      'font-family: "Arial";'+	// cal--offMonth-background-color 
	      'font-size: 11pt;'+	// cal--offMonth-color 
	      'color: #ff0000;'+	// cal--offMonth-font-family
	      'text-decoration: none;'+	// cal--offMonth-font-size 
	      'background-color: #FFD163;'+	// cal--offMonth-font-weight 
	      'border-top-width: 0px;'+	// cal--offMonth-text-align 
	      'border-right-width: 0px;'+	// cal--offMonth-vertical-align 
	      'border-bottom-width: 0px;'+
	      'border-left-width: 0px;'+
	      'border-top-style: none;'+
	      'border-right-style: none;'+
	      'border-bottom-style: solid;'+
	      'border-left-style: none;'+
	      'border-bottom-color: #FFFFFF;'+
	      'text-align: center;'+
	      'vertical-align: middle;'
	      

	    )
	  if(isIE) {
		  goStyle['DaySelected'] = StyleInfo.rules[StyleInfo.rules.length - 1].style
	  } else {
		  goStyle['DaySelected'] = StyleInfo.cssRules[StyleInfo.cssRules.length - 1].style
	  }
	  fnLoadCSSDefault('cal--selectedDay-background-color', 'calSelectedDayBackgroundColor', goStyle['DaySelected'], 'backgroundColor')
	  fnLoadCSSDefault('cal--selectedDay-color',            'calSelectedDayColor',           goStyle['DaySelected'], 'color')
	  fnLoadCSSDefault('cal--selectedDay-font-family',      'calSelectedDayFontFamily',      goStyle['DaySelected'], 'fontFamily')
	  fnLoadCSSDefault('cal--selectedDay-font-size',        'calSelectedDayFontSize',        goStyle['DaySelected'], 'fontSize')
	  fnLoadCSSDefault('cal--selectedDay-font-weight',      'calSelectedDayFontWeight',      goStyle['DaySelected'], 'fontWeight')
	  fnLoadCSSDefault('cal--selectedDay-text-align',       'calSelectedDayTextAlign',       goStyle['DaySelected'], 'textAlign')
	  fnLoadCSSDefault('cal--selectedDay-vertical-align',   'calSelectedDayVerticalAlign',   goStyle['DaySelected'], 'verticalAlign')
	}
	function fnLoadCSSDefault(sCSSProp, sScriptProp, oStyleRule, sStyleRuleProp) {
		var styleValue = null;
		if(isIE) {
			styleValue = element.currentStyle[sCSSProp];
		} else {
			styleValue = window.getComputedStyle(element,null).getPropertyValue(sCSSProp);
		}
		if (styleValue) {
			oStyleRule[sStyleRuleProp] = styleValue
		}
		element.style[sScriptProp] = oStyleRule[sStyleRuleProp]
	}
	function fnGetPropertyDefaults() {
		var x
		var oDate = new Date()

		giDay = oDate.getDate()
		giMonth = oDate.getMonth() + 1
		giYear = oDate.getFullYear()
		if (giYear < 100)
			giYear += 1900
		if (this.year) {
			if (!isNaN(parseInt(element.year)))
				giYear = parseInt(element.year)
			if (giYear < giMinYear)
				giYear = giMinYear
			if (giYear > giMaxYear)
				giYear = giMaxYear
		}
		fnCheckLeapYear(giYear)
		if (element.month) {
			if (!isNaN(parseInt(element.month)))
				giMonth = parseInt(element.month)
			if (giMonth < 1)
				giMonth = 1
			if (giMonth > 12)
				giMonth = 12
		}
		if (element.day) {
			if (!isNaN(parseInt(element.day)))
				giDay = parseInt(element.day)
			if (giDay < 1)
				giDay = 1
			if (giDay > gaMonthDays[giMonth - 1])
				giDay = gaMonthDays[giMonth - 1]
		}
		if (element.monthLength) {
			switch (element.monthLength.toLowerCase()) {
			case 'short':
				giMonthLength = 0
				break
			case 'long':
				giMonthLength = 1
				break
			}
		}
		if (element.dayLength) {
			switch (element.dayLength.toLowerCase()) {
			case 'short':
				giDayLength = 0
				break
			case 'medium':
				giDayLength = 1
				break
			case 'long':
				giDayLength = 1
				break
			}
		}
		if (element.firstDay) {
			if ((element.firstDay >= 0) && (element.firstDay <= 6))
				giFirstDay = element.firstDay
		}
		if (element.gridCellEffect) {
			switch (element.gridCellEffect.toLowerCase()) {
			case 'raised':
				giGridCellEffect = 'raised'
				break
			case 'flat':
				giGridCellEffect = 'flat'
				break
			case 'sunken':
				giGridCellEffect = 'sunken'
				break
			}
		}
		if (element.gridLinesColor)
			gsGridLinesColor = element.gridLinesColor
		if (element.showDateSelectors)
			gbShowDateSelectors = (element.showDateSelectors) ? true : false
		if (element.showDays)
			gbShowDays = (element.showDays) ? true : false
		if (element.showTitle)
			gbShowTitle = (element.showTitle) ? true : false
		if (element.showHorizontalGrid)
			gbShowHorizontalGrid = (element.showHorizontalGrid) ? true : false
		if (element.showVerticalGrid)
			gbShowVerticalGrid = (element.showVerticalGrid) ? true : false
		if (element.valueIsNull)
			gbValueIsNull = (element.valueIsNull) ? true : false
		if (element.name)
			gsName = element.name
		if (element.readOnly)
			gbReadOnly = (element.readOnly) ? true : false
	}
	function fnSetDate(iDay, iMonth, iYear) {
		var bValueChange = false;
		if (gbValueIsNull) {
			gbValueIsNull = false
			fnFireOnPropertyChange("propertyName", "valueIsNull")
		}
		if (iYear < giMinYear)
			iYear = giMinYear
		if (iYear > giMaxYear)
			iYear = giMaxYear
		if (giYear != iYear) {
			fnCheckLeapYear(iYear)
		}
		if (iMonth < 1)
			iMonth = 1
		if (iMonth > 12)
			iMonth = 12
		if (iDay < 1)
			iDay = 1
		if (iDay > gaMonthDays[giMonth - 1])
			iDay = gaMonthDays[giMonth - 1]
		if ((giDay == iDay) && (giMonth == iMonth) && (giYear == iYear))
			return

		else
			bValueChange = true
		if (giDay != iDay) {
			giDay = iDay
			fnFireOnPropertyChange("propertyName", "day")
		}
		if ((giYear == iYear) && (giMonth == iMonth)) {
			goCurrentDayCell.className = 'Day_' + uniqueID
			goCurrentDayCell = gaDayCell[giStartDayIndex + iDay - 1]
			goCurrentDayCell.className = 'DaySelected_' + uniqueID
			giDay = iDay
		} else {
			if (giYear != iYear) {
				giYear = iYear
				fnFireOnPropertyChange("propertyName", "year")
				fnUpdateYearSelect()
			}
			if (giMonth != iMonth) {
				giMonth = iMonth
				fnFireOnPropertyChange("propertyName", "month")
				fnUpdateMonthSelect()
			}
			fnUpdateTitle()
			fnFillInCells()
		}
		if (bValueChange)
			fnFireOnPropertyChange("propertyName", "value")
	}
	function fnUpdateTitle() {
		var oTitleCell = element.children[0].rows[0].cells[0]
		if (gbShowTitle)
			oTitleCell.innerHTML = gaMonthNames[giMonthLength][giMonth - 1] + " " + giYear
		else
			oTitleCell.innerText = ' '
	}
	function fnUpdateDayTitles() {
		var dayTitleRow = element.children[0].rows[1].cells[0].children[0].rows[0]
		var iCell = 0
		for (i = giFirstDay; i < 7; i++) {
			goDayTitleRow.cells[iCell++].innerText = gaDayNames[giDayLength][i]
		}
		for (i = 0; i < giFirstDay; i++) {
			goDayTitleRow.cells[iCell++].innerText = gaDayNames[giDayLength][i]
		}
	}
	function fnUpdateMonthSelect() {
		goMonthSelect.options[giMonth - 1].selected = true
	}
	function fnBuildMonthSelect() {
		var newMonthSelect
		newMonthSelect = document.createElement("SELECT")
		goMonthSelect.parentNode.replaceChild(newMonthSelect, goMonthSelect)
		goMonthSelect = newMonthSelect
		for (i = 0; i < 12; i++) {
			e = document.createElement("OPTION")
			e.text = gaMonthNames[giMonthLength][i]
			goMonthSelect.options.add(e)
		}
		goMonthSelect.options[giMonth - 1].selected = true
		if(goMonthSelect.addEventListener) {
			goMonthSelect.addEventListener("change",fnMonthSelectOnChange,false);
		} else {
			goMonthSelect.attachEvent("onchange", fnMonthSelectOnChange);
		}
	}
	function fnMonthSelectOnChange() {
		iMonth = goMonthSelect.selectedIndex + 1
		fnSetDate(giDay, iMonth, giYear)
	}
	function fnUpdateYearSelect() {
		goYearSelect.options[giYear - giMinYear].selected = true
	}
	function fnBuildYearSelect() {
		var newYearSelect
		newYearSelect = document.createElement("SELECT")
		goYearSelect.parentNode.replaceChild(newYearSelect, goYearSelect)
		goYearSelect = newYearSelect
		for (i = giMinYear; i <= giMaxYear; i++) {
			e = document.createElement("OPTION")
			e.text = i
			goYearSelect.options.add(e)
		}
		goYearSelect.options[giYear - giMinYear].selected = true
		if(goYearSelect.addEventListener) {
			goYearSelect.addEventListener("change",fnYearSelectOnChange,false);
		} else {
			goYearSelect.attachEvent("onchange", fnYearSelectOnChange);
		}
	}
	function fnYearSelectOnChange() {
		iYear = goYearSelect.selectedIndex + giMinYear
		fnSetDate(giDay, giMonth, iYear)
	}
	function fnCheckLeapYear(iYear) {
		gaMonthDays[1] = (((!(iYear % 4)) && (iYear % 100)) || !(iYear % 400)) ? 29 : 28
	}
	function fnFillInCells() {
		var iDayCell = 0
		var iLastMonthIndex, iNextMonthIndex
		var iLastMonthTotalDays
		var iStartDay
		fnCheckLeapYear(giYear)
		iLastMonthDays = gaMonthDays[((giMonth - 1 == 0) ? 12 : giMonth - 1) - 1]
		iNextMonthDays = gaMonthDays[((giMonth + 1 == 13) ? 1 : giMonth + 1) - 1]
		iLastMonthYear = (giMonth == 1) ? giYear - 1 : giYear
		iLastMonth = (giMonth == 1) ? 12 : giMonth - 1
		iNextMonthYear = (giMonth == 12) ? giYear + 1 : giYear
		iNextMonth = (giMonth == 12) ? 1 : giMonth + 1
		var oDate = new Date(giYear, (giMonth - 1), 1)
		iStartDay = oDate.getDay() - giFirstDay
		if (iStartDay < 1)
			iStartDay += 7
		iStartDay = iLastMonthDays - iStartDay + 1
		for (i = iStartDay; i <= iLastMonthDays; i++, iDayCell++) {
			gaDayCell[iDayCell].innerText = i
			if (gaDayCell[iDayCell].className != 'OffDay_' + uniqueID)
				gaDayCell[iDayCell].className = 'OffDay_' + uniqueID
			gaDayCell[iDayCell].day = i
			gaDayCell[iDayCell].month = iLastMonth
			gaDayCell[iDayCell].year = iLastMonthYear
		}
		giStartDayIndex = iDayCell
		for (i = 1; i <= gaMonthDays[giMonth - 1]; i++, iDayCell++) {
			gaDayCell[iDayCell].innerText = i
			if (giDay == i) {
				goCurrentDayCell = gaDayCell[iDayCell]
				gaDayCell[iDayCell].className = 'DaySelected_' + uniqueID
			} else {
				if (gaDayCell[iDayCell].className != 'Day_' + uniqueID)
					gaDayCell[iDayCell].className = 'Day_' + uniqueID
			}
			gaDayCell[iDayCell].day = i
			gaDayCell[iDayCell].month = giMonth
			gaDayCell[iDayCell].year = giYear
		}
		for (i = 1; iDayCell < 42; i++, iDayCell++) {
			gaDayCell[iDayCell].innerText = i
			if (gaDayCell[iDayCell].className != 'OffDay_' + uniqueID)
				gaDayCell[iDayCell].className = 'OffDay_' + uniqueID
			gaDayCell[iDayCell].day = i
			gaDayCell[iDayCell].month = iNextMonth
			gaDayCell[iDayCell].year = iNextMonthYear
		}
	}
	function fnOnClick(e) {
		e = window.event || e;
		e = e.srcElement || e.target;
		if (e.tagName == "TD") {
			if (gbReadOnly || (!e.day))
				return // The calendar is read only
			if ((e.year < giMinYear) || (e.year > giMaxYear))
				return

			fnSetDate(e.day, e.month, e.year)
		}
	}
	function fnOnSelectStart(e) {
		e = window.event || e;
		e.returnValue = false
		e.cancelBubble = true
	}
	function fnOnReadyStateChange() {
		gbLoading = (readyState != "complete")
	}
	function fnOnPropertyChange(e) {
		e = window.event || e;
		if (e.getAttribute("propertyName").substring(0, 5) == 'style') {
			switch (e.getAttribute("propertyName")) {
			case 'style.calTitleBackgroundColor':
				goStyle['WholeCalendar'].backgroundColor = element.style.calTitleBackgroundColor
				goStyle['Title'].backgroundColor = element.style.calTitleBackgroundColor
				break
			case 'style.calTitleColor':
				goStyle['Title'].color = element.style.calTitleColor
				break
			case 'style.calTitleFontFamily':
				goStyle['Title'].fontFamily = element.style.calTitleFontFamily
				break
			case 'style.calTitleFontSize':
				goStyle['Title'].fontSize = element.style.calTitleFontSize
				break
			case 'style.calTitleFontWeight':
				goStyle['Title'].fontWeight = element.style.calTitleFontWeight
				break
			case 'style.calTitleTextAlign':
				goStyle['Title'].textAlign = element.style.calTitleTextAlign
				break
			case 'style.calDayTitleBackgroundColor':
				goStyle['DayTitle'].backgroundColor = element.style.calDayTitleBackgroundColor
				break
			case 'style.calDayTitleColor':
				goStyle['DayTitle'].color = element.style.calDayTitleColor
				break
			case 'style.calDayTitleFontFamily':
				goStyle['DayTitle'].fontFamily = element.style.calDayTitleFontFamily
				break
			case 'style.calDayTitleFontSize':
				goStyle['DayTitle'].fontSize = element.style.calDayTitleFontSize
				break
			case 'style.calDayTitleFontWeight':
				goStyle['DayTitle'].fontWeight = element.style.calDayTitleFontWeight
				break
			case 'style.calDayTitleTextAlign':
				goStyle['DayTitle'].textAlign = element.style.calDayTitleTextAlign
				break
			case 'style.calOffMonthBackgroundColor':
				goStyle['OffDay'].backgroundColor = element.style.calOffMonthBackgroundColor
				break
			case 'style.calOffMonthColor':
				goStyle['OffDay'].color = element.style.calOffMonthColor
				break
			case 'style.calOffMonthFontFamily':
				goStyle['OffDay'].fontFamily = element.style.calOffMonthFontFamily
				break
			case 'style.calOffMonthFontSize':
				goStyle['OffDay'].fontSize = element.style.calOffMonthFontSize
				break
			case 'style.calOffMonthFontWeight':
				goStyle['OffDay'].fontWeight = element.style.calOffMonthFontWeight
				break
			case 'style.calOffMonthTextAlign':
				goStyle['OffDay'].textAlign = element.style.calOffMonthTextAlign
				break
			case 'style.calOffMonthVerticalAlign':
				goStyle['OffDay'].verticalAlign = element.style.calOffMonthVerticalAlign
				break
			case 'style.calCurrentMonthBackgroundColor':
				goStyle['Day'].backgroundColor = element.style.calCurrentMonthBackgroundColor
				break
			case 'style.calCurrentMonthColor':
				goStyle['Day'].color = element.style.calCurrentMonthColor
				break
			case 'style.calCurrentMonthFontFamily':
				goStyle['Day'].fontFamily = element.style.calCurrentMonthFontFamily
				break
			case 'style.calCurrentMonthFontSize':
				goStyle['Day'].fontSize = element.style.calCurrentMonthFontSize
				break
			case 'style.calCurrentMonthFontWeight':
				goStyle['Day'].fontWeight = element.style.calCurrentMonthFontWeight
				break
			case 'style.calCurrentMonthTextAlign':
				goStyle['Day'].textAlign = element.style.calCurrentMonthTextAlign
				break
			case 'style.calCurrentMonthVerticalAlign':
				goStyle['Day'].verticalAlign = element.style.calCurrentMonthVerticalAlign
				break
			case 'style.calSelectedDayBackgroundColor':
				goStyle['DaySelected'].backgroundColor = element.style.calSelectedDayBackgroundColor
				break
			case 'style.calSelectedDayColor':
				goStyle['DaySelected'].color = element.style.calSelectedDayColor
				break
			case 'style.calSelectedDayFontFamily':
				goStyle['DaySelected'].fontFamily = element.style.calSelectedDayFontFamily
				break
			case 'style.calSelectedDayFontSize':
				goStyle['DaySelected'].fontSize = element.style.calSelectedDayFontSize
				break
			case 'style.calSelectedDayFontWeight':
				goStyle['DaySelected'].fontWeight = element.style.calSelectedDayFontWeight
				break
			case 'style.calSelectedDayTextAlign':
				goStyle['DaySelected'].textAlign = element.style.calSelectedDayTextAlign
				break
			case 'style.calSelectedDayVerticalAlign':
				goStyle['DaySelected'].verticalAlign = element.style.calSelectedDayVerticalAlign
				break
			}
		}
	}
	function fnFireOnPropertyChange(name1, value1) {
		if (document.createEventObject){
		    // dispatch for IE
		    var evt = document.createEventObject();
		    evt.setAttribute(name1, value1)
		    //return element.fireEvent('on'+event,evt)
		    return element.fireEvent('onpropertychange',evt)
	    } else{
		    // dispatch for firefox + others
		    var evt = document.createEvent("HTMLEvents");
		    //evt.setAttribute(name1, value1)
		    evt.initEvent("propertychange", true, true ); // event type,bubbling,cancelable
		    return !element.dispatchEvent(evt);
	    }
	}
	function fnUpdateGridColors() {
		switch (gsGridCellEffect) {
		case "raised":
			goStyle['OffDay'].borderLeftColor = 'white'
			goStyle['OffDay'].borderTopColor = 'white'
			goStyle['OffDay'].borderRightColor = 'black'
			goStyle['OffDay'].borderBottomColor = 'black'
			goStyle['Day'].borderLeftColor = 'white'
			goStyle['Day'].borderTopColor = 'white'
			goStyle['Day'].borderRightColor = 'black'
			goStyle['Day'].borderBottomColor = 'black'
			goStyle['DaySelected'].borderLeftColor = 'white'
			goStyle['DaySelected'].borderTopColor = 'white'
			goStyle['DaySelected'].borderRightColor = 'black'
			goStyle['DaySelected'].borderBottomColor = 'black'
			break
		case "flat":
			goStyle['OffDay'].borderLeftColor = goStyle['OffDay'].backgroundColor
			goStyle['OffDay'].borderTopColor = goStyle['OffDay'].backgroundColor
			goStyle['OffDay'].borderRightColor = (gbShowVerticalGrid) ? gsGridLinesColor : goStyle['Day'].backgroundColor
			goStyle['OffDay'].borderBottomColor = (gbShowHorizontalGrid) ? gsGridLinesColor : goStyle['Day'].backgroundColor
			goStyle['Day'].borderLeftColor = goStyle['Day'].backgroundColor
			goStyle['Day'].borderTopColor = goStyle['Day'].backgroundColor
			goStyle['Day'].borderRightColor = (gbShowVerticalGrid) ? gsGridLinesColor : goStyle['Day'].backgroundColor
			goStyle['Day'].borderBottomColor = (gbShowHorizontalGrid) ? gsGridLinesColor : goStyle['Day'].backgroundColor
			goStyle['DaySelected'].borderLeftColor = goStyle['DaySelected'].backgroundColor
			goStyle['DaySelected'].borderTopColor = goStyle['DaySelected'].backgroundColor
			goStyle['DaySelected'].borderRightColor = (gbShowVerticalGrid) ? gsGridLinesColor : goStyle['Day'].backgroundColor
			goStyle['DaySelected'].borderBottomColor = (gbShowHorizontalGrid) ? gsGridLinesColor : goStyle['Day'].backgroundColor
			break
		case "sunken":
			goStyle['OffDay'].borderLeftColor = 'black'
			goStyle['OffDay'].borderTopColor = 'black'
			goStyle['OffDay'].borderRightColor = 'white'
			goStyle['OffDay'].borderBottomColor = 'white'
			goStyle['Day'].borderLeftColor = 'black'
			goStyle['Day'].borderTopColor = 'black'
			goStyle['Day'].borderRightColor = 'white'
			goStyle['Day'].borderBottomColor = 'white'
			goStyle['DaySelected'].borderLeftColor = 'black'
			goStyle['DaySelected'].borderTopColor = 'black'
			goStyle['DaySelected'].borderRightColor = 'white'
			goStyle['DaySelected'].borderBottomColor = 'white'
			break
		default:
			throw "日期参照可能不正常，请再试。"
		}
	}

	jQuery("#cal").each(function(e) {
		element = this;
		// 绑定事件
		jQuery(this).select(fnOnSelectStart);
		jQuery(this).click(fnOnClick);
		jQuery(this).bind("propertychange", function(e) {
			fnOnPropertyChange(e);
		});
		jQuery(this).bind("readystatechange", function(e) {
			fnOnReadyStateChange(e);
		});
		this.fnGetValue = fnGetValue;
		fnGetPropertyDefaults();
		fnCreateStyleSheets();
		fnCreateCalendarHTML();
		fnUpdateTitle();
		fnUpdateDayTitles();
		fnBuildMonthSelect();
		fnBuildYearSelect();
		fnFillInCells();
	});
});