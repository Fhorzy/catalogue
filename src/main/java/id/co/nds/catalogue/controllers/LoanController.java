package id.co.nds.catalogue.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.LoanEntity;
import id.co.nds.catalogue.exceptions.ClientExceptions;
import id.co.nds.catalogue.models.LoanModel;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.services.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    public LoanService loanService;

    @PostMapping("/add")
    public ResponseEntity<ResponseModel> postLoan(@RequestBody LoanModel loanModel) {
        try {
            LoanEntity loan = loanService.doLoan(loanModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("New loan is successfully added");
            response.setData(loan);

            return ResponseEntity.ok(response);
        } catch (ClientExceptions e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }
}
