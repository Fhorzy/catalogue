package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import id.co.nds.catalogue.entities.LoanEntity;
import id.co.nds.catalogue.models.LoanModel;
import id.co.nds.catalogue.repos.LoanRepo;
import id.co.nds.catalogue.validators.LoanValidator;
import id.co.nds.catalogue.validators.UserValidator;

@Service
public class LoanService implements Serializable {
    @Autowired
    public LoanRepo loanRepo;

    @Autowired
    public UserService userService;

    public LoanValidator loanValidator = new LoanValidator();
    public UserValidator userValidator = new UserValidator();

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
    public LoanEntity doLoan(LoanModel loanModel) throws Exception {
        loanValidator.notNullCheckLoanId(loanModel.getId());

        loanValidator.nullCheckCustomerName(loanModel.getCustomerName());
        loanValidator.validateCustomerName(loanModel.getCustomerName().trim());
        loanValidator.nullCheckUserId(loanModel.getUserId());
        loanValidator.validateUserId(loanModel.getUserId());
        
        // check if user exists or not
        // userService.getUserById(loanModel.getUserId());
        
        LoanEntity loan = new LoanEntity();
        loan.setCustomerName(loanModel.getCustomerName().trim());
        loan.setRoleId(userService.getUserRoleById(loanModel.getUserId()));
        loan.setUserId(loanModel.getUserId());
        loan.setLoanAmount(loanModel.getLoanAmount());
        loan.setLoanTerm(loanModel.getLoanTerm());
        loan.setInterestRate(loanModel.getInterestRate());
        loan.setTotalLoan(loanModel.getLoanAmount() / loanModel.getLoanTerm() * loanModel.getInterestRate() / 100);
        loan.setStartDate(new Timestamp(System.currentTimeMillis()));
        
        return loanRepo.save(loan);
    }

}
