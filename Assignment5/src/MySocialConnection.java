
/**
Author: Chaz Davies B00862866

 This class implements SocialCOnnections. The graph is an edge vertex list using two linked lists.

 **/


import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class MySocialConnection implements SocialConnections{
    protected List<Person> people = new LinkedList<>();

    protected class Person {
        protected String name;
        protected List<Person> connections = new LinkedList<>();
        protected boolean visited;

        protected  Person parent = null;
        public Person(String name) {
            this.name = name;
            people.add(this);
        }

        public String getName() {
            return name;
        }
    }


    /*
    adds person to graph
     */
    @Override
    public boolean addPerson(String name) {


        Person per = null;

        // if person already exists, return false and dont add new person
        for(Person person : people){
            if(person.getName().equals(name)){
                //set person to fperson
                per = person;
            }

        }

        if(per != null){
            return false;
        }

        Person newPerson = new Person(name);

        return true;
    }

    /*
    removes person from graph
     */
    @Override
    public boolean removePerson(String name) throws PersonNotFoundException {
        Person per = null;

        //if person does not exist return false
        for(Person person : people){
            if(person.getName().equals(name)){
                //set person to fperson
                per = person;
            }

        }

        if(per == null){
            return false;
        }

        // add person to vertex list
        people.remove(per);

        return true;
    }

    /*
    connects two people in graph
     */
    @Override
    public void connectPeople(String firstPerson, String secondPerson) throws PersonNotFoundException {

        Person fPerson = null;
        Person sPerson = null;



        for(Person person : people){
            if(person.getName().equals(firstPerson)){
                //set person to fperson
                fPerson = person;
            }
            if(person.getName().equals(secondPerson)){
                //set person to sperson
                sPerson = person;
            }
        }

        // if either of the two people dont exist, throw error
        if(fPerson == null){
            throw new PersonNotFoundException(firstPerson + " not Found");
        }
        if(sPerson == null){
            throw new PersonNotFoundException(secondPerson + " not Found");
        }


        // add each the person to each others edge list
        fPerson.connections.add(sPerson);
        sPerson.connections.add(fPerson);


    }

    /*
    returns all connections of person
     */
    @Override
    public List<String> getConnections(String name) throws PersonNotFoundException {

        Person per = null;


        for(Person person : people){
            if(person.getName().equals(name)){
                //set person to fperson
                per = person;
            }

        }

        if(per == null){
            throw new PersonNotFoundException(name + " not found");
        }


        // create list of names to store the persons connections

        List<String> connections = new LinkedList<String>();
        Person person = null;

        //find person
        for(Person p : people){
            if(p.getName().equals(name)){
                person = p;
            }
        }

        // go through the persons connections listand add the names to the new list
        for(Person c : person.connections){
            connections.add(c.getName());
        }

        return connections;
    }

    /*
    returns the shortest number of degrees from two people
     */
    @Override
    public int getMinimumDegreeOfSeparation(String firstPerson, String secondPerson) throws PersonNotFoundException {

        Person fPerson = null;
        Person sPerson = null;

        for(Person person : people){
            if(person.getName().equals(firstPerson)){
                //set person to fperson
                fPerson = person;
            }
            if(person.getName().equals(secondPerson)){
                //set person to sperson
                sPerson = person;
            }
        }


        if(fPerson == null){
            throw new PersonNotFoundException(firstPerson + " not found");
        }
        if(sPerson == null){
            throw new PersonNotFoundException(secondPerson + " not found");
        }



        // call shortest path and add it to the list
        List<Person> shortestPath = shortestPath(fPerson, sPerson, fPerson.connections);


        //reset visited and parent to default
        for(Person p : people){
            p.visited = false;
            p.parent = null;
        }

        // if people are not connected in the same graph return -1 and print error

        if(shortestPath == null){
            System.out.println(fPerson.name + " and " + sPerson.name + " are not connected");
            return -1;
        }



        return shortestPath.size();

    }

    //healper method
    public List<Person> shortestPath(Person fPerson, Person sPerson, List<Person> people) throws PersonNotFoundException {
        searchBFS(fPerson, sPerson, fPerson.connections);

        List<Person> path = new LinkedList<Person>();


        //make a path of parents from sPerson to fPerson


        while (sPerson != fPerson) {

            if(sPerson == null){
                return null;
            }

            path.add(sPerson);
            sPerson = sPerson.parent;

        }

        return path;
    }


    //helper method

    public void searchBFS(Person firstPerson, Person secondPerson, List<Person> people) throws PersonNotFoundException {

        Queue<Person> queue = new LinkedList<Person>();

        queue.add(firstPerson);

        firstPerson.visited = true;

        while (queue.size() > 0) {

            Person fPerson = queue.remove();
            List<Person> adjacencies = fPerson.connections;


            for (Person p : adjacencies) {


                if (!p.visited) {


                    p.parent = fPerson; // parent link


                    if (secondPerson == p) {
                        return;
                    }

                    p.visited = true;

                    queue.add(p);

                }
            }

        }

    }
    @Override



    public List<String> getConnectionsToDegree(String name, int maxLevel) throws PersonNotFoundException {

        Person fPerson = null;

        // check if person exists
        for(Person person : people){
            if(person.getName().equals(name)){
                //set person to fperson
                fPerson = person;
                break;
            }
        }

        if(fPerson == null){
            throw new PersonNotFoundException(name + " not found");
        }

        ArrayList<String> connectionsList = new ArrayList<String>();


        // add all people with a path less than or equa to maxLevel to the connections list
        for(Person p : people){
            if(getMinimumDegreeOfSeparation(fPerson.name, p.name) <= maxLevel && getMinimumDegreeOfSeparation(fPerson.name, p.name) >= 0){
                connectionsList.add(p.name);
            }
        }

        return connectionsList;
    }


    @Override
    public boolean areWeAllConnected() {

        int size = people.size();

        // call function countVertices for each person, they should all equals the siue of the graph if everyone is connected

        for(Person p : people){
            if(countVertices(p) != size){
                return  false;
            }

            //set visited to false
            for(Person x : people){
                x.visited = false;
            }
        }



        return true;
    }

    public int countVertices(Person v){
        int count = 0;

        // if someone is not yet visited, increase count
        if(!v.visited){
            v.visited = true;

            count = 1;

            for(Person p : v.connections){
                count = count + countVertices(p);
            }
        }

        return count;
    }
}
