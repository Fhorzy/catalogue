package id.co.nds.catalogue.models;

public class LoanModel {
    private String id;
    private Integer userId;
    private String roleId;
    private Double loanAmount;
    private Integer loanTerm;
    private Double interestRate;
    private String customerName;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public Double getLoanAmount() {
        return loanAmount;
    }
    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }
    public Integer getLoanTerm() {
        return loanTerm;
    }
    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }
    public Double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

   
}
