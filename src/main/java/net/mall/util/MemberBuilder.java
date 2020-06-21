package net.mall.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.OutputStream;
import java.util.List;

/****供应商采购商
 */
public class MemberBuilder {



    OutputStream outStream;


    List<ExcelWriteMember> excelWriteMembers;

    /***
     * 供应商采购商
     */
    public class ExcelWriteMember extends BaseRowModel {


        /***用户名
         */
        @ExcelProperty(value = "用户名",index = 0)
        private String username;

        /***E-mail*/
        @ExcelProperty(value = "E-mail",index = 2)
        private String email;

        @ExcelProperty(value = "手机",index = 2)
        private String mobile;

        /***手续费
         */
        @ExcelProperty(value = "公司名称",index = 1)
        private String name;


        @ExcelProperty(value = "公司地址",index = 3)
        private String address;

        @ExcelProperty(value = "电话",index = 2)
        private String phone;

        @ExcelProperty(value = "法人姓名",index = 2)
        private String legalPerson;

        @ExcelProperty(value = "法人身份证图片正面",index = 2)
        private String idCardImage;

        @ExcelProperty(value = "纳税人识别号",index = 2)
        private String identificationNumber;

        @ExcelProperty(value = "开户行地址",index = 2)
        private String bankAddress;

        @ExcelProperty(value = "银行开户名",index = 2)
        private String bankName;

        @ExcelProperty(value = "银行账号",index = 2)
        private String bankAccount;

        @ExcelProperty(value = "邮编",index = 2)
        private String zipCode;

        @ExcelProperty(value = "组织机构代码",index = 2)
        private String organizationCode;

        @ExcelProperty(value = "组织机构代码图片",index = 2)
        private String organizationImage;

        @ExcelProperty(value = "税务登记证图片",index = 2)
        private String taxImage;

        @ExcelProperty(value = "营业执照号",index = 2)
        private String licenseNumber;

        @ExcelProperty(value = "营业执照图片",index = 2)
        private String licenseImage;

    }







    public void writeExcel(String filePath){
        Sheet  sheet = new Sheet(1,1,ExcelWriteMember.class);
        ExcelWriter excelBuilder = new ExcelWriter(outStream,ExcelTypeEnum.XLSX);
        excelBuilder.write(excelWriteMembers,sheet);
        excelBuilder.finish();
    }
}
