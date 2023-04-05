/**
 *   Kyle Jones
 *   SongList
 *   Project #3
 *   CMSC 256-Spring2023
 *
 *   Program is designed to create a playlist of songs using singly linked lists, and when given an artist,their songs appear in alphabetical order.
 */
package cmsc256;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import bridges.base.SLelement;
import bridges.connect.Bridges;

public class SongList implements cmsc256.List<MySong>, java.lang.Iterable {
    private SLelement head;
    private SLelement tail;
    private int listSize;
    public String name;


    public static void main(String[] args) throws Exception {

        // Create Bridges object,  fill in credentials
        Bridges bridges = new Bridges(3, "joneskl12",
                "1180709966832");
        // set title
        bridges.setTitle("Song List");

        // set  description
        bridges.setDescription("A list of Songs from the BRIDGES song dataset.");

        // create the linked list elements with Songs
        SLelement st0 = new SLelement<>();
        SLelement st1 = new SLelement<>();
        SLelement st2 = new SLelement<>();
        SLelement st3 = new SLelement<>();
        SLelement st4 = new SLelement<>();

        //  link the elements
        st0.setNext(st1);
        st1.setNext(st2);
        st2.setNext(st3);
        st3.setNext(st4);

        st0.setLabel((String) st0.getValue());
        st1.setLabel((String) st1.getValue());
        st2.setLabel((String) st2.getValue());
        st3.setLabel((String) st3.getValue());
        st4.setLabel((String) st4.getValue());


        Iterator<MySong> iter = st0.iterator();
        while (iter.hasNext()) {
            System.out.println("\t" + iter.next());
        }


        // tell Bridges the head of the list
        bridges.setDataStructure(st0);

    }

    /**
     * default constructor for the SongList object
     */
    public SongList(){
        this.name = "";
        this.tail = null;
        this.head = null;
    }

    /**
     * parameterized constructor for the Songlist object
     * @param name
     */
    public SongList(String name) {
        this.name = name;
        this.tail = null;
        this.head = null;

    }

    /**
     * returns the name of the playlist.
     * @return
     */
    public String getPlaylistName() {
        return this.name;
    }

    /**
     * clears the playlist
     */
    @Override
    public void clear() {
        tail = null;
        head = null;
        name = "";
        listSize = 0;
    }


    /**
     * inserts the song in the playlist at a given position in the playlist.
     * @param it
     * @param position
     * @return
     */
    @Override
    public boolean insert(MySong it, int position) {
        int count = 0;
        SLelement temp;
        SLelement curr = head;

        if (head == null) {
            head = new SLelement<>(it);
            tail = head;
            listSize++;
            return true;
        }
        while (curr.getNext() != null) {
            if (count == position) {
                SLelement<MySong> newNode = new SLelement<>(it);
                temp = curr.getNext();
                curr.setNext(newNode);
                newNode.setNext(temp);
                listSize++;
                return true;
            }
            curr = curr.getNext();
            count++;
        }
        return false;
    }

    /**
     * adds a song to the end of the playlist.
     * @param it
     * @return
     */
    @Override
    public boolean add(MySong it) {
        SLelement<MySong> newNode = new SLelement<>(it);
        if (head == null) {
            head = newNode;
            tail = head;
            listSize++;
            return true;
        }
        tail.setNext(newNode);
        tail = newNode;
        listSize++;
        return true;
    }

    /**
     * removes a song at a given postion.
     * @param position
     * @return
     */
    @Override
    public MySong remove(int position) {
        int count = 0;
        SLelement temp = head;
        SLelement curr = head;
        if (position == 0) {
            head = head.getNext();
            listSize--;
            return (MySong) curr.getValue();
        }
        while (curr != null) {
            if (count == position) {
                if (position == listSize - 1) {
                    tail = temp;
                    temp.setNext(null);
                    listSize--;
                    return (MySong) curr.getValue();
                }
                temp.setNext(curr.getNext());
                listSize--;
                return (MySong) curr.getValue();
            }
            temp = curr;
            curr = curr.getNext();
            count++;
        }
        return null;
    }

    /**
     * returns the given size of the playlist
     * @return
     */
    @Override
    public int size() {
        return listSize;
    }

    /**
     * checks to see if the playlist is empty or not.
     * @return
     */
    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }

    /**
     * checks to see if the playlist contains a certain song or not.
     * @param target
     * @return
     */
    @Override
    public boolean contains(MySong target) {
        SLelement curr = head;
        while (curr != null) {
            if (curr.getValue() == target) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    /**
     * returns the song in a given position of the playlist.
     * @param position
     * @return
     */
    @Override
    public MySong getValue(int position) {
        int count = 0;
        SLelement curr = head;
        if (position == 0) {
            head = head.getNext();
            return (MySong) curr.getValue();
        }
        while (curr != null) {
            if (count == position) {
                return (MySong) curr.getValue();
            }
            curr = curr.getNext();
            count++;
        }
        return null;
    }

    /**
     * iterates through the playlist and sorts the playlist in alphabetical order.
     * @return
     */
    @Override
    public Iterator iterator() {
        ArrayList<MySong> list = new ArrayList<>();
        SLelement curr = head;

        while (curr != null) {
            list.add((MySong) curr.getValue());
            curr = curr.getNext();
        }
        list.sort(MySong::compareTo);
        return list.iterator();
    }

    /**
     * outputs the artists discography within the playlist, if artist does not have any songs within the playlist, output states so.
     * @param artist
     * @return
     */
    public String getSongsByArtist(String artist) {
        boolean hasSongByArtist = false;
        StringBuilder result = new StringBuilder();
        Iterator<MySong> iter = iterator();
        while(iter.hasNext()){
            MySong m = iter.next();
            if (m.artist == artist){
                hasSongByArtist = true;
                result.append(String.format("Title: %s Album: %s\n", m.song, m.album));
            }

        }
       if (!hasSongByArtist){
           return String.format("There are no songs by %s in this playlist.", artist);
       } else
        return result.toString();

    }


    private class SongIterator  {

    }
}
