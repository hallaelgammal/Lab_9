/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dell
 */
//add & addAll & geterrors protect encapsulation
public class ValidationResult {
    private final List<ValidationError> errors=new ArrayList<>();//final 3shan el reference can't point to different list bs we can add and remove elements 3ady
    public void add(ValidationError e){
        errors.add(e);
    }
    public void addAll(List<ValidationError>list){
        errors.addAll(list);
    }
    public boolean isValid(){
        return errors.isEmpty();
    }
    public List<ValidationError> getErrors(){
        return errors;
    }
    public static ValidationResult merge(ValidationResult a,ValidationResult b){
        ValidationResult out=new ValidationResult();
        out.addAll(a.getErrors());
        out.addAll(b.getErrors());
        return out;
    }
}
