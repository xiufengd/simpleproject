package com.xiufengd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiufengd.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
@Repository
public class SysUser {

    @Id
    @Column(name = "id_")
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 公司ID
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 用户来源类别
     */
    @Column(name = "user_source_type")
    private String userSourceType;

    /**
     * 登陆帐户
     */
    @Column(name = "account_")
    private String account;

    /**
     * 密码
     */
    @Column(name = "password_")
    private String password;

    /**
     * 电话
     */
    @Column(name = "phone_")
    private String phone;

    @Column(name = "email_")
    private String email;

    /**
     * 性别(0:未知;1:男;2:女)
     */
    @Column(name = "sex_")
    private Integer sex;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    @Column(name = "avatar_")
    private String avatar;

    /**
     * 固定电话
     */
    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    /**
     * 用户类型: 业务字典（内部，外部）
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * 部门编号
     */
    @Column(name = "dept_id")
    private String deptId;

    @Column(name = "title_")
    private String title;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "address_")
    private String address;

    @Column(name = "remark_")
    private String remark;

    /**
     * 锁定标志(1:锁定;0:激活)
     */
    @Column(name = "locked_")
    private Boolean locked;

    @Column(name = "enable_")
    private Boolean enable;

    /**
     * 审核人
     */
    @Column(name = "APPROVAL_BY")
    private String approvalBy;

    /**
     * 审核时间
     */
    @Column(name = "APPROVAL_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvalTime;

    /**
     * 审核是否通过
     */
    @Column(name = "IS_APPROVAL")
    private String isApproval;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Column(name = "update_by")
    private String updateBy;

    /**
     * 推荐人手机号
     */
    private String referrer;

    /**
     * @return id_
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取公司ID
     *
     * @return company_id - 公司ID
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 设置公司ID
     *
     * @param companyId 公司ID
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取用户来源类别
     *
     * @return user_source_type - 用户来源类别
     */
    public String getUserSourceType() {
        return userSourceType;
    }

    /**
     * 设置用户来源类别
     *
     * @param userSourceType 用户来源类别
     */
    public void setUserSourceType(String userSourceType) {
        this.userSourceType = userSourceType;
    }

    /**
     * 获取登陆帐户
     *
     * @return account_ - 登陆帐户
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置登陆帐户
     *
     * @param account 登陆帐户
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取密码
     *
     * @return password_ - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取电话
     *
     * @return phone_ - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return email_
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取性别(0:未知;1:男;2:女)
     *
     * @return sex_ - 性别(0:未知;1:男;2:女)
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别(0:未知;1:男;2:女)
     *
     * @param sex 性别(0:未知;1:男;2:女)
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return avatar_
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取固定电话
     *
     * @return OFFICE_PHONE - 固定电话
     */
    public String getOfficePhone() {
        return officePhone;
    }

    /**
     * 设置固定电话
     *
     * @param officePhone 固定电话
     */
    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    /**
     * 获取用户类型: 业务字典（内部，外部）
     *
     * @return user_type - 用户类型: 业务字典（内部，外部）
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置用户类型: 业务字典（内部，外部）
     *
     * @param userType 用户类型: 业务字典（内部，外部）
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取部门编号
     *
     * @return dept_id - 部门编号
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * 设置部门编号
     *
     * @param deptId 部门编号
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * @return title_
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return PROVINCE
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return CITY
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return DISTRICT
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return address_
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return remark_
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取锁定标志(1:锁定;0:激活)
     *
     * @return locked_ - 锁定标志(1:锁定;0:激活)
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * 设置锁定标志(1:锁定;0:激活)
     *
     * @param locked 锁定标志(1:锁定;0:激活)
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * @return enable_
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * @param enable
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取审核人
     *
     * @return APPROVAL_BY - 审核人
     */
    public String getApprovalBy() {
        return approvalBy;
    }

    /**
     * 设置审核人
     *
     * @param approvalBy 审核人
     */
    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy;
    }

    /**
     * 获取审核时间
     *
     * @return APPROVAL_TIME - 审核时间
     */
    public Date getApprovalTime() {
        return approvalTime;
    }

    /**
     * 设置审核时间
     *
     * @param approvalTime 审核时间
     */
    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    /**
     * 获取审核是否通过
     *
     * @return IS_APPROVAL - 审核是否通过
     */
    public String getIsApproval() {
        return isApproval;
    }

    /**
     * 设置审核是否通过
     *
     * @param isApproval 审核是否通过
     */
    public void setIsApproval(String isApproval) {
        this.isApproval = isApproval;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return update_by
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取推荐人手机号
     *
     * @return referrer - 推荐人手机号
     */
    public String getReferrer() {
        return referrer;
    }

    /**
     * 设置推荐人手机号
     *
     * @param referrer 推荐人手机号
     */
    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id == null ? null : '\"' + id + '\"');
        sb.append(",\"companyId\":")
                .append(companyId == null ? null : '\"' + companyId + '\"');
        sb.append(",\"roleId\":")
                .append(roleId == null ? null : '\"' + roleId + '\"');
        sb.append(",\"userSourceType\":")
                .append(userSourceType == null ? null : '\"' + userSourceType + '\"');
        sb.append(",\"account\":")
                .append(account == null ? null : '\"' + account + '\"');
        sb.append(",\"password\":")
                .append(password == null ? null : '\"' + password + '\"');
        sb.append(",\"phone\":")
                .append(phone == null ? null : '\"' + phone + '\"');
        sb.append(",\"email\":")
                .append(email == null ? null : '\"' + email + '\"');
        sb.append(",\"sex\":")
                .append(sex);
        sb.append(",\"userName\":")
                .append(userName == null ? null : '\"' + userName + '\"');
        sb.append(",\"avatar\":")
                .append(avatar == null ? null : '\"' + avatar + '\"');
        sb.append(",\"officePhone\":")
                .append(officePhone == null ? null : '\"' + officePhone + '\"');
        sb.append(",\"userType\":")
                .append(userType);
        sb.append(",\"deptId\":")
                .append(deptId == null ? null : '\"' + deptId + '\"');
        sb.append(",\"title\":")
                .append(title == null ? null : '\"' + title + '\"');
        sb.append(",\"province\":")
                .append(province == null ? null : '\"' + province + '\"');
        sb.append(",\"city\":")
                .append(city == null ? null : '\"' + city + '\"');
        sb.append(",\"district\":")
                .append(district == null ? null : '\"' + district + '\"');
        sb.append(",\"address\":")
                .append(address == null ? null : '\"' + address + '\"');
        sb.append(",\"remark\":")
                .append(remark == null ? null : '\"' + remark + '\"');
        sb.append(",\"locked\":")
                .append(locked);
        sb.append(",\"enable\":")
                .append(enable);
        sb.append(",\"approvalBy\":")
                .append(approvalBy == null ? null : '\"' + approvalBy + '\"');
        sb.append(",\"approvalTime\":")
                .append(approvalTime == null ? null : '\"' + DateUtil.getStringDate(approvalTime, "yyyy-MM-dd HH:mm:ss") + '\"');
        sb.append(",\"isApproval\":")
                .append(isApproval == null ? null : '\"' + isApproval + '\"');
        sb.append(",\"createTime\":")
                .append(createTime == null ? null : '\"' + DateUtil.getStringDate(createTime, "yyyy-MM-dd HH:mm:ss") + '\"');
        sb.append(",\"createBy\":")
                .append(createBy == null ? null : '\"' + createBy + '\"');
        sb.append(",\"updateTime\":")
                .append(updateTime == null ? null : '\"' + DateUtil.getStringDate(updateTime, "yyyy-MM-dd HH:mm:ss") + '\"');
        sb.append(",\"updateBy\":")
                .append(updateBy == null ? null : '\"' + updateBy + '\"');
        sb.append(",\"referrer\":")
                .append(referrer == null ? null : '\"' + referrer + '\"');
        sb.append('}');
        return sb.toString();

    }
}