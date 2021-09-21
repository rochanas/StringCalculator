
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {
    
    public StringCalculator(){}
        
    public int add(String numbers) throws Exception
    {
        String delimiter = getDelimiter(numbers);
        String numbersWithoutLeadingDelimiter = getNumbersWithoutLeadingDelimiter(delimiter, numbers);
        
        if(hasCustomDelimiter(numbers, numbersWithoutLeadingDelimiter))
            return add(numbersWithoutLeadingDelimiter, getSplitNumbers(delimiter, ","));
        
        //new lines can be present in numbers, but won't be the delimiter since we're using a comma - remove them.
        String numbersWithoutNewLines = numbers.replace("\n", "");                        
        return add(numbersWithoutNewLines, delimiter);
    }
        
    private int add(String numbers, String toSplitOn) throws Exception
    {    
        List<String> delimiters = new ArrayList<>();
        delimiters.add(toSplitOn);
        return add(numbers, delimiters);
    }
        
    private int add(String numbers, List<String> toSplitOn) throws Exception
    {        
        if(numbers.isEmpty())
            return 0;
        
        List<String> numbersToAddList = getSplitNumbers(numbers, toSplitOn);
        int addedTogether = addNumbersTogetherFromSplitArray(numbersToAddList);        
        
        return addedTogether;
    }
    
    private boolean hasCustomDelimiter(String numbers, String numbersWithoutLeadingDelimiter)
    {
        return !numbers.equalsIgnoreCase(numbersWithoutLeadingDelimiter);
    }
    
    private String getNumbersWithoutLeadingDelimiter(String delimiter, String numbers)
    {
        String splitBeginner = "//" + delimiter + "\n";
        if(numbers.startsWith(splitBeginner))
            return numbers.replace(splitBeginner,"");
        return numbers;
    }
    
    private String getDelimiter(String numbers)
    {
         //if the string doesn't start with // and contain a new line, we'll assume the delimiter is a comma
        if(numbers.length() < 2 || !(numbers.substring(0, 2).equalsIgnoreCase("//")) || !numbers.contains("\n"))
            return ",";
        
        return numbers.substring(2, numbers.indexOf("\n"));
    }
    
    private int addNumbersTogetherFromSplitArray(List<String> numbersToAddList) throws Exception
    {
        int addedTogether = 0;
        List<String> negativeNumbers = new ArrayList<>();
        
        for(String i : numbersToAddList){
            if(i.trim().isEmpty())
                continue;
            
            int toAdd = 0;
            try{
                toAdd = Integer.parseInt(i.trim());
            } catch(Exception ex){
                System.out.println(" ERROR: Couldn't add " + i);
                continue;
            }
               
            if(toAdd > 1000) //not throwing exception here, wasn't in reqs
                continue;

            if(toAdd < 0){
                negativeNumbers.add(i);
                continue;
            }

            addedTogether += toAdd;
        } 
        
        if(!negativeNumbers.isEmpty()){
            throw new Exception(getNegativeError(negativeNumbers));            
        }            
        
        return addedTogether;
    }
        
    private String getNegativeError(List<String> negativeNumbers)
    {
        String negativeString = "";
        for(String i : negativeNumbers)
            negativeString += i + ", ";
        negativeString = " Negatives not allowed, please remove the following numbers: " + negativeString.substring(0, negativeString.length() -2);
        return negativeString;
    }
    
    private List<String> getSplitNumbers(String stringToSplit, List<String> delimiters)
    {
        List<String> split = getSplitNumbers(stringToSplit, delimiters.get(0));
        if(delimiters.size() == 1)
            return split;
        
        for(int i = 1; i < delimiters.size(); i++){
            String delimiter = delimiters.get(i);
            List<String> newSplit = new ArrayList<>();
            for(String s : split){
                newSplit.addAll(getSplitNumbers(s, delimiter));
            }            
            split.removeAll(split);
            split.addAll(newSplit);
        }
        
        return split;
    }
    
    private List<String> getSplitNumbers(String stringToSplit, String delimiter)
    {
        if(stringToSplit == null || stringToSplit.isEmpty())
            return new ArrayList<>();
        
        String escapedDelimiter = Pattern.quote(delimiter);
        String[] numbersToAddObject = stringToSplit.split(escapedDelimiter);
        ArrayList<String> numbersToAddList = new ArrayList<>(Arrays.asList(numbersToAddObject));
        return numbersToAddList;        
    } 
    
}
