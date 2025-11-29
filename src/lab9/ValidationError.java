/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

import java.util.List;

/**
 *
 * @author Abdallah
 */
public class ValidationError {
    public enum Type {ROW, COL, BOX}
    private final Type type;
    private final int index;
    private final int value;
    private final List<String> locations;
    
    public ValidationError(Type type, int index, int value, List<String> locations ) {
        this.type = type;
        this.index = index;
        this.value = value;
        this.locations = locations;
    }
    
    public Type getType()
    {
        return type;
    }
    public int getIndex()
    {
        return index;
    }
    public int getValue()
    {
        return value;
    }
    public List<String> getLocations()
    {
        return locations;
    }
    
    @Override
    public String toString()
    {
       return String.format(
               "%s %d, #%d, %s" ,
               type.name(),
               index,
               value,
               locations.toString()
       );
               
    }
}

