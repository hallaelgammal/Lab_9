/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;
import java.util.List;
/**
 *
 * @author Dell
 */
public interface Validator {
    List<ValidationError> validate() throws InterruptedException;
}
