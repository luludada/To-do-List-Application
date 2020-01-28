package model;

import model.exceptions.IncorrectDateFormatException;
import model.exceptions.TooManyItemsException;
import model.exceptions.TooManyUrgentItemException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TodoList extends Subject implements Loadable, Saveable {
    private static final int UrgentItemLimit = 5;
    private static final int ItemLimit = 25;

    private ArrayList<Item> todoList;
    private Date currentDate = new Date();

    public TodoList() {
        super();
        addObserver(new Report());
        todoList = new ArrayList<>();
    }

    // REQUIRES: user input according to required format
    // MODIFIES: this
    // EFFECTS: add a new item to the list, throws TooManyUrgentItemException and TooManyItemsException
    //          if date input is in wrong format
    public void addItem(Item i) throws TooManyUrgentItemException, TooManyItemsException {
        int urgentCount = countTotalUrgentItem();
        if (i.getType().equals("Urgent") && urgentCount >= UrgentItemLimit) {
            throw new TooManyUrgentItemException("Too Many Urgent Items in the List.");
        }
        if (todoList.size() >= ItemLimit) {
            throw new TooManyItemsException("Too Many Item in the List");
        }
        todoList.add(i);
        notifyObservers(i);
    }

    // EFFECTS: count the number of urgent item in the todolist
    private int countTotalUrgentItem() {
        int countUrgentItem = 0;
        for (Item i: todoList) {
            if (i.getType().equals("Urgent")) {
                countUrgentItem += 1;
            }
        }
        return countUrgentItem;
    }

    // MODIFIES: RegularItem
    // EFFECTS: change the status of item at itemIndex to complete, print error message if index is invalid
    public void crossedOffItem(int itemIndex) {
        if (itemIndex < 0 || itemIndex + 1 > todoList.size()) {
            System.out.println("Invalid Index");
        } else {
            Item regularItem = todoList.get(itemIndex);
            regularItem.setStatus("Completed");
        }
    }

    // MODIFIES: RegularItem
    // EFFECTS: set the status of the item to overdue;
    //          throw IncorrectDateFormatException if catch ParseException
    public void checkOverDue() throws IncorrectDateFormatException {
        for (Item i : todoList) {
            DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
            Date date;
            try {
                date = format.parse(i.getDueDate());
                if (date.before(currentDate)) {
                    if (!(i.getStatus().equals("Completed"))) {
                        i.setStatus("Overdue");
                    }
                }
            } catch (ParseException e) {
                throw new IncorrectDateFormatException();
            }
        }
    }

    // MODIFIES: RegularItem
    // EFFECTS: set the status of the item to overdue;
    //          throw IncorrectDateFormatException if catch ParseException
    public void checkInProgress() throws IncorrectDateFormatException {
        for (Item i : todoList) {
            DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
            Date date;
            try {
                date = format.parse(i.getDueDate());
                if (date.after(currentDate)) {
                    if (!(i.getStatus().equals("Completed"))) {
                        i.setStatus("In-progress");
                    }
                }
            } catch (ParseException e) {
                throw new IncorrectDateFormatException();
            }
        }
    }


    // EFFECTS: return size of the todolist
    public int size() {
        return todoList.size();
    }

    // EFFECTS: return item at index i of the todolist
    public Item getItem(int i) {
        return todoList.get(i);
    }

    public ArrayList<Item> getTodoList() {
        return todoList;
    }

    // EFFECTS: split ArraryList<String>
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(" : ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: input file name to exist
    // MODIFIES: this
    // EFFECTS: print in todolist item in file
    @Override
    public void save(String fileName) throws IOException {
        List<String> newLines = new ArrayList<>();
        PrintWriter writer = new PrintWriter("src/saveItemFile.txt","UTF-8");
        for (Item i : todoList) {
            newLines.add(i.getName() + " : " + i.getStatus() + " : "
                    + i.getDueDate() + " : " + i.getType());
        }
        for (String line : newLines) {
            writer.println(line);
        }
        writer.close();
    }


    // REQUIRES: input file name to exist
    // EFFECTS: print file items into todolist
    @Override
    public void load(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line: lines) {
            ArrayList<String> partsOfLine = splitString(line);
            if (partsOfLine.get(3).equals("Urgent")) {
                todoList.add(new UrgentItem(partsOfLine.get(0),partsOfLine.get(1), partsOfLine.get(2)));
            }
            if (partsOfLine.get(3).equals("Regular")) {
                todoList.add(new RegularItem(partsOfLine.get(0),partsOfLine.get(1), partsOfLine.get(2)));
            }
            if (partsOfLine.get(3).equals("Academic")) {
                todoList.add(new AcademicItem(partsOfLine.get(0),partsOfLine.get(1), partsOfLine.get(2)));
            }
        }
    }


    // REQUIRES: input file name to exist
    // MODIFIES: this
    // EFFECTS: empty input file
    @Override
    public void emptyFile(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(fileName,"UTF-8");
        writer.print("");
        writer.close();
    }


}