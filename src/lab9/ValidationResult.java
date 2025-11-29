/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
/**
 *
 * @author Dell
 */
//add & addAll & geterrors protect encapsulation
public class ValidationResult {
    private final List<ValidationError> errors=Collections.synchronizedList(new ArrayList<>());//final 3shan el reference can't point to different list bs we can add and remove elements 3ady
    public synchronized void add(ValidationError e){
        errors.add(e);
    }
    public synchronized void addAll(List<ValidationError>list){
        errors.addAll(list);
    }
    public boolean isValid(){
        return errors.isEmpty();
    }
    public synchronized List<ValidationError> getErrors(){
        return errors;
    }
    public static ValidationResult merge(ValidationResult a,ValidationResult b){
        ValidationResult out=new ValidationResult();
        out.addAll(a.getErrors());
        out.addAll(b.getErrors());
        return out;
    }
  

    // Optional: clear all errors
    public synchronized void clear() {
        errors.clear();
    }

    // Optional: thread-safe string representation
    @Override
    public synchronized String toString() {
        if (errors.isEmpty()) return "No validation errors.";
        StringBuilder sb = new StringBuilder();
        for (ValidationError e : errors) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }
}
