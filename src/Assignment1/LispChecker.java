package Assignment1;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class LispChecker {
    public static void main(String[] args) throws Exception {
        System.out.println("Availity Homework Assignment 1: Lisp Checker");
        System.out.printf("Please input LISP code to validate: ");

        String lispString = System.console().readLine();

        System.out.println(validateString(lispString));
    }

    private static boolean validateString(final String lispString) {
        boolean isValid = true;
        
        if(!lispString.isEmpty() && !lispString.equals(null)) {
            //We're only worried about parentheses, so let's filter everything else out.
            final Set<Character> parenthesesFilter = new HashSet<>(Arrays.asList(')', '('));

            List<String> filteredValues = lispString.chars()
                        .filter(x -> parenthesesFilter.contains((char) x))
                        .mapToObj(x -> "" + (char) x)
                        .collect(Collectors.toList());

            //We'll use the Deque to keep track of our opening parentheses
            Deque<String> queue = new ArrayDeque<>(filteredValues.size());

            try {
                for(String x : filteredValues) {
                    if(x.equals("(")) {
                        queue.push(x);
                    }
                    else {
                        queue.pop();
                    }
                }
            }
            catch(NoSuchElementException e) {
                //We'll land here if the order is wrong or if there are too many closing parentheses
                isValid = false;
            }

            if(!queue.isEmpty()) {
                //There were too many opening parentheses
                isValid = false;
            }
        }

        return isValid;
    }
}
