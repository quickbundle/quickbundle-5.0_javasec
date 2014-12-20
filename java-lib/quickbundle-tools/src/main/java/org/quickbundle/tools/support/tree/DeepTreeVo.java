package org.quickbundle.tools.support.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class DeepTreeVo {
    /**
     * mapAttribute 表示: 存放属性数据的Map
     */
    private Map<String, Object> mapAttribute;
    
    /**
     * lKeyIndex 表示: 体现key值先后顺序的List
     */
    private List<String> lKeyIndex;

    /**
     * 构造函数: 初始化属性，私有构造函数
     * 
     */
    private DeepTreeVo() {
        this.mapAttribute = new HashMap<String, Object>();
        this.lKeyIndex = new ArrayList<String>();
        this.addAttribute("id", "");
        this.addAttribute("text", "");
        this.addAttribute("hasChild", "");
        this.addAttribute("xmlSource", "");
        this.addAttribute("defaultOpen", "0");
        this.addAttribute("logoImagePath", "");
        this.addAttribute("statusFlag", "1");
        this.addAttribute("title", "");
        this.addAttribute("hrefPath", "");
        this.addAttribute("target", "_blank");
        this.addAttribute("orderStr", "");
        this.addAttribute("returnValue", "");
        this.addAttribute("isSelected", "");
        this.addAttribute("indeterminate", "");
        this.addAttribute("thisType", "");
        this.addAttribute("detailedType", "");
        this.addAttribute("isSubmit", "");
        this.addAttribute("parentId", "");
        this.addAttribute("childIds", "");
    }

    /**
     * 构造函数: 带4个必需参数的构造函数
     * @param id 唯一标识，如果returnValue为""，id作为returnValue
     * @param text 显示的文本，也作为文本返回
     * @param hasChild 是否有子节点，软栽入时显示加号(可继续展开)或点(叶子节点)
     * @param xmlSource 子节点数据的xml路径，只有hasChild等于1才有意义
     */
    public DeepTreeVo(String id, String text, String hasChild, String xmlSource) {
        this();
        this.addAttribute("id", id);
        this.addAttribute("text", text);
        this.addAttribute("hasChild", hasChild);
        this.addAttribute("xmlSource", xmlSource);
    }

    /**
     * 功能: 为DeepTreeVo增加属性，访问时有序(按属性加入的先后顺序)
     *
     * @param attributeName 属性名称
     * @param attributeValue 属性值
     */
    public void addAttribute(String attributeName, String attributeValue) {
        if(!lKeyIndex.contains(attributeName)) {
            this.lKeyIndex.add(attributeName);            
        }
        getAttributeMap().put(attributeName, attributeValue);
    }
    
    /**
     * 功能: 获得DeepTreeVo的属性
     *
     * @param attributeName 属性名称
     * @return
     */
    public String getAttribute(String attributeName) {
        Object attributeValue = getAttributeMap().get(attributeName);
        return attributeValue == null ? null : String.valueOf(attributeValue);
    }
    
    /**
     * 功能: 获得存放属性数据的Map,不带顺序的,谨慎使用
     *
     * @return
     */
    private Map<String, Object> getAttributeMap() {
        return this.mapAttribute;
    }
    
    /**
     * 功能: 获得体现key值先后顺序的List迭代器
     *
     * @return
     */
    public Iterator getAttributeMapIterator() {
        return lKeyIndex.iterator();
    }

    //开始DeepTreeVo属性的getter和setter方法
    
    /**
     * 功能: 必填项，标识唯一Id
     *
     * @return
     */
    public String getId() {
        return getAttribute("id");
    }
    
    /**
     * 功能: 必填项，标识唯一Id
     *
     * @param id
     */
    public void setId(String id) {
        addAttribute("id", id);
    }
    
    /**
     * 功能: 必填项，显示文本内容
     *
     * @return
     */
    public String getText() {
        return getAttribute("text");
    }
    /**
     * 功能: 必填项，显示文本内容
     *
     * @param text
     */
    public void setText(String text) {
        addAttribute("text", text);
    }
    
    /**
     * 功能: 必填项，此冗余值表示是否有子节点
     *
     * @return
     */
    public String getHasChild() {
        return getAttribute("hasChild");
    }
    /**
     * 功能: 必填项，此冗余值表示是否有子节点
     *
     * @param hasChild
     */
    public void setHasChild(String hasChild) {
        addAttribute("hasChild", hasChild);
    }
    
    /**
     * 功能: 必填项（如果hasChild大于0），子节点的数据源
     *
     * @return
     */
    public String getXmlSource() {
        return getAttribute("xmlSource");
    }
    /**
     * 功能: 必填项（如果hasChild大于0），子节点的数据源
     *
     * @param xmlSource
     */
    public void setXmlSource(String xmlSource) {
        addAttribute("xmlSource", xmlSource);
    }
    
    /**
     * 功能: 可选，是否继续自动展开本节点，只有当hasChild为1并且本xml有子节点时才有效；默认等于0
     *
     * @return
     */
    public String getDefaultOpen() {
        return getAttribute("defaultOpen");
    }
    /**
     * 功能: 可选，是否继续自动展开本节点，只有当hasChild为1并且本xml有子节点时才有效；默认等于0
     *
     * @param defaultOpen
     */
    public void setDefaultOpen(String defaultOpen) {
        addAttribute("defaultOpen", defaultOpen);
    }
    
    /**
     * 功能: 可选，可以在文字前边加一个图标，注意要写上文件名；默认等于空
     *
     * @return
     */
    public String getLogoImagePath() {
        return getAttribute("logoImagePath");
    }
    /**
     * 功能: 可选，可以在文字前边加一个图标，注意要写上文件名；默认等于空
     *
     * @param logoImagePath
     */
    public void setLogoImagePath(String logoImagePath) {
        addAttribute("logoImagePath", logoImagePath);
    }
    
    /**
     * 功能: 可选，数据启用禁用状态；默认等于1
     *
     * @return
     */
    public String getStatusFlag() {
        return getAttribute("statusFlag");
    }
    /**
     * 功能: 可选，数据启用禁用状态；默认等于1
     *
     * @param statusFlag
     */
    public void setStatusFlag(String statusFlag) {
        addAttribute("statusFlag", statusFlag);
    }
    
    /**
     * 功能: 可选，鼠标放上去时提示内容；默认等于text
     *
     * @return
     */
    public String getTitle() {
        return getAttribute("title");
    }
    /**
     * 功能: 可选，鼠标放上去时提示内容；默认等于text
     *
     * @param title
     */
    public void setTitle(String title) {
        addAttribute("title", title);
    }
    
    /**
     * 功能: 可选，超链接地址；默认等于空
     *
     * @return
     */
    public String getHrefPath() {
        return getAttribute("hrefPath");
    }
    /**
     * 功能: 可选，超链接地址；默认等于空
     *
     * @param hrefPath
     */
    public void setHrefPath(String hrefPath) {
        addAttribute("hrefPath", hrefPath);
    }
    
    /**
     * 功能: 可选，超链接的打开方式；默认等于_blank
     *
     * @return
     */
    public String getTarget() {
        return getAttribute("target");
    }
    /**
     * 功能: 可选，超链接的打开方式；默认等于_blank
     *
     * @param target
     */
    public void setTarget(String target) {
        addAttribute("target", target);
    }
    
    /**
     * 功能: 可选，排序编码，升序；默认等于空
     *
     * @return
     */
    public String getOrderStr() {
        return getAttribute("orderStr");
    }
    /**
     * 功能: 可选，排序编码，升序；默认等于空
     *
     * @param orderStr
     */
    public void setOrderStr(String orderStr) {
        addAttribute("orderStr", orderStr);
    }
    
    /**
     * 功能: 可选，提交时的返回值；默认等于Id
     *
     * @return
     */
    public String getReturnValue() {
        return getAttribute("returnValue");
    }
    /**
     * 功能: 可选，提交时的返回值；默认等于Id
     *
     * @param returnValue
     */
    public void setReturnValue(String returnValue) {
        addAttribute("returnValue", returnValue);
    }
    
    /**
     * 功能: 可选，是否已经被选中；默认等于0
     *
     * @return
     */
    public String getIsSelected() {
        return getAttribute("isSelected");
    }
    /**
     * 功能: 可选，是否已经被选中；默认等于0
     *
     * @param isSelected
     */
    public void setIsSelected(String isSelected) {
        addAttribute("isSelected", isSelected);
    }
    
    /**
     * 功能: 可选，是否为不确定状态；默认等于0
     *
     * @return
     */
    public String getIndeterminate() {
        return getAttribute("indeterminate");
    }
    /**
     * 功能: 可选，是否为不确定状态；默认等于0
     *
     * @param indeterminate
     */
    public void setIndeterminate(String indeterminate) {
        addAttribute("indeterminate", indeterminate);
    }
    
    /**
     * 功能: 可选，节点类型；默认等于空
     *
     * @return
     */
    public String getThisType() {
        return getAttribute("thisType");
    }
    /**
     * 功能: 可选，节点类型；默认等于空
     *
     * @param thisType
     */
    public void setThisType(String thisType) {
        addAttribute("thisType", thisType);
    }
    
    /**
     * 功能: 可选，更具体的节点类型；默认等于空
     *
     * @return
     */
    public String getDetailedType() {
        return getAttribute("detailedType");
    }
    /**
     * 功能: 可选，更具体的节点类型；默认等于空
     *
     * @param detailedType
     */
    public void setDetailedType(String detailedType) {
        addAttribute("detailedType", detailedType);
    }
    
    /**
     * 功能: 可选，表示是否可以被提交；默认等于1
     *
     * @return
     */
    public String getIsSubmit() {
        return getAttribute("isSubmit");
    }
    /**
     * 功能: 可选，表示是否可以被提交；默认等于1
     *
     * @param isSubmit
     */
    public void setIsSubmit(String isSubmit) {
        addAttribute("isSubmit", isSubmit);
    }
    
    /**
     * 功能: 不填，预留给程序处理，表示父节点Id；默认等于空
     *
     * @return
     */
    public String getParentId() {
        return getAttribute("parentId");
    }
    /**
     * 功能: 不填，预留给程序处理，表示父节点Id；默认等于空
     *
     * @param parentId
     */
    public void setParentId(String parentId) {
        addAttribute("parentId", parentId);
    }
    
    /**
     * 功能: 不填，预留给程序处理，表示子节点集合；默认等于空
     *
     * @return
     */
    public String getChildIds() {
        return getAttribute("childIds");
    }
    /**
     * 功能: 不填，预留给程序处理，表示子节点集合；默认等于空
     *
     * @param childIds
     */
    public void setChildIds(String childIds) {
        addAttribute("childIds", childIds);
    }
    
    //结束DeepTreeVo属性的getter和setter方法
}