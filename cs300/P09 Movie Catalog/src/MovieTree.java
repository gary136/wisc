//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define MovieTree
// Course: CS 300 Spring 2021
//
// Author: Lee Hung Ting
// Email: hlee864@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class implements the methods of class MovieTree
 *
 */

public class MovieTree {
  private BSTNode<Movie> root; // root of this movie BST
  private int size; // size of this movie tree

  /**
   * Checks whether this binary search tree (BST) is empty
   * 
   * @return true if this MovieTree is empty, false otherwise
   */
  public boolean isEmpty() {
    // TODO Complete the implementation of this method.
    if (root==null) return true;
    return false; 
  }

  /**
   * Returns the number of movies stored in this BST.
   * 
   * @return the size of this MovieTree
   */
  public int size() {
    // TODO Complete the implementation of this method.
    return size; 
  }


  /**
   * Adds a new movie to this MovieTree
   * 
   * @param newMovie a new movie to add to this BST.
   * @return true if the newMovie was successfully added to this BST, and returns false if there is
   *         a match with this movie already stored in this BST.
   */
  public boolean addMovie(Movie newMovie) {
    // TODO Complete the implementation of this method.
    if (isEmpty()) { // Add newMovie to an empty MovieTree
      // TODO /* MISSING CODE */
      root = new BSTNode(newMovie);
      size++;
      return true;
    } else { // Add newMovie to an non-empty MovieTree
      // TODO /* MISSING CODE */
      boolean x = addMovieHelper(newMovie, root);
      if (x) size++;
      return x; 
      // [Hint]: call addMovieTreeHelper to help implement this behavior.
      
    }
  }

  /**
   * Recursive helper method to add a new movie to a MovieTree rooted at current.
   * 
   * @param current  The "root" of the subtree we are inserting new movie into.
   * @param newMovie The movie to be added to a BST rooted at current.
   * @return true if the newMovie was successfully added to this MovieTree, false otherwise
   */
  protected static boolean addMovieHelper(Movie newMovie, BSTNode<Movie> current) {
    // TODO Complete the implementation of this method.

    // equal
    if (newMovie.equals(current.getData())) return false;
    
    // less and leaf
    if (newMovie.compareTo(current.getData()) < 0 &&
        current.getLeft()==null
        ) {
      current.setLeft(new BSTNode(newMovie));
      return true;
    }
    
    // greater and leaf
    else if (newMovie.compareTo(current.getData()) > 0 &&
        current.getRight()==null
        ) {
      current.setRight(new BSTNode(newMovie));
      return true;
    }
    
    
    // internal
    else {
      if (newMovie.compareTo(current.getData()) < 0) 
        return addMovieHelper(newMovie, current.getLeft());
      else
        return addMovieHelper(newMovie, current.getRight());
    }
  }

  /**
   * Returns a String representation of all the movies stored within this BST in the increasing
   * order, separatingd by a newline "\n". For instance
   * 
   * "[(Year: 1988) (Rate: 7.0) (Name: Light Years)]" + "\n" + 
   * "[(Year: 2015) (Rate: 6.5) (Name: Cinderella)]" + "\n"
   * 
   * @return a String representation of all the movies stored within this BST sorted in an
   *         increasing order with respect to the result of Movie.compareTo() method (year, rating,
   *         name). Returns an empty string "" if this BST is empty.
   */
  @Override
  public String toString() {
    return toStringHelper(root);
  }

  /**
   * Recursive helper method which returns a String representation of the BST rooted at current. An
   * example of the String representation of the contents of a MovieTree is provided in the
   * description of the above toString() method.
   * 
   * @param current reference to the current movie within this BST (root of a subtree)
   * @return a String representation of all the movies stored in the sub-tree rooted at current in
   *         increasing order with respect to the result of Movie.compareTo() method (year, rating,
   *         name). Returns an empty String "" if current is null.
   */
  protected static String toStringHelper(BSTNode<Movie> current) {
    if (current==null) {
      return "";
    }
    // TODO Complete the implementation of this method.
    String l = toStringHelper(current.getLeft()).toString();
    String c = current.getData().toString()+"\n";
    String r = toStringHelper(current.getRight()).toString();
//    if ((l+c+r).equals("")) System.out.println("empty");
    return l+c+r; // remove this statement.
  }

  /**
   * Computes and returns the height of this BST, counting the number of NODES from root to the
   * deepest leaf.
   * 
   * @return the height of this Binary Search Tree
   */
  public int height() {
    return heightHelper(root);
  }

  /**
   * Recursive helper method that computes the height of the subtree rooted at current counting the
   * number of nodes and NOT the number of edges from current to the deepest leaf
   * 
   * @param current pointer to the current BSTNode within a MovieTree (root of a subtree)
   * @return height of the subtree rooted at current
   */
  protected static int heightHelper(BSTNode<Movie> current) {
    // TODO Complete this implementation of this method
    if (current==null) {
      return 0;
    }
    // TODO Complete the implementation of this method.
    int l = heightHelper(current.getLeft());
    int c = 1;
    int r = heightHelper(current.getRight());
    int depth = (l>r) ? l : r;
    return c + depth; // remove this statement.

  }

  /**
   * Checks whether this MovieTree contains a movie 
   * given its name, production year, and rating.
   * 
   * @param year   year of production of the movie to search
   * @param rating rating of the movie to search
   * @param name   name of the movie to search
   * @return true if there is a match with this movie in this BST, and false otherwise
   */
  public boolean contains(int year, double rating, String name) {
    // TODO complete the implementation of this method
    // [HINT] Use the recursive helper containshHelper method
    if (isEmpty()) return false;
    Movie target = new Movie(year, rating, name);
    return containsHelper(target, root); 
    // remove this statement. Added to let this code to compile without errors
  }

  /**
   * Recursive helper method to search wether there is a match with a given movie in the subtree
   * rooted at current
   * 
   * @param target  a reference to a movie we are searching for a match in the BST rooted at
   *                current.
   * @param current "root" of the subtree we are checking whether it contains a match to target.
   * @return true if match found and false otherwise
   */
  protected boolean containsHelper(Movie target, BSTNode<Movie> current) {
    // TODO Complete the implementation of this method
    if (current==null) return false;
    
    // equal
    if (current.getData().equals(target)) return true;
    
    // less 
    if (current.getData().compareTo(target) > 0) {
      return containsHelper(target, current.getLeft());
    }
    
    // greater 
    if (current.getData().compareTo(target) < 0) {
      return containsHelper(target, current.getRight());
    }
    
    return false; // remove this statement (added to let this code to compile)
  }


  /**
   * Gets the best (maximum) movie in this BST
   * 
   * @return the best (largest) movie (the most recent, highest 
   * rated, and having the largest name) 
   * in this MovieTree, and null if this tree is empty.
   */
  public Movie getBestMovie() throws NullPointerException{
    // TODO Complete the implementation of this method
    if (isEmpty()) return null;
    
    // Feel free to implement either the iterative 
    // OR the recursive version of this method.
    BSTNode<Movie> curr = root;
    if (curr==null) 
      throw new NullPointerException("Empty movie collection!");
    while (curr.getRight()!=null) curr = curr.getRight();
    return curr.getData();
  }

  public Movie brokenGetBestMovie() {
    // not return null when empty list
    BSTNode<Movie> curr = root;
    while (curr.getRight()!=null) curr = curr.getRight();
    return curr.getData();
  }
  /**
   * Search for movies given the year and minimum rating as lookup key.
   * 
   * @param year   production year of a movie
   * @param rating rating value of a movie
   * @return a list of movies whose year equals our lookup year key and having a rating greater or
   *         equal to a given rating.
   * @throws a NoSuchElementException with a descriptive error message if there is no Movie found in
   *           this BST having the provided year and a rating greater or equal to the provided
   *           rating
   */
  public ArrayList<Movie> lookup(int year, double rating) throws NoSuchElementException {
    // TODO
    if (isEmpty()) return null;
    ArrayList<Movie> movieList = new ArrayList<Movie>();
    // call lookupHelper given the year, rating, the root of 
    // this MovieTree and an empty arrayList
    lookupHelper(year, rating, root, movieList); 
    
    if (movieList.size()==0) 
      throw new NoSuchElementException("no movie matches");
    // If no match found by the lookupHelper throw a NoSuchElementException 
    // with a descriptive error message
    
    // else return the list of movies
    return movieList; // remove this statement. Added to let this code to compile without errors
  }

  /**
   * Recursive helper method to lookup the list of movies given 
   * their year of production and a minimum value of rating
   * 
   * @param year      the year we would like to search for a movie
   * @param rating    the minimum rating we would like to search for a movie
   * @param movieList an arraylist which stores the list of movies matching our search criteria
   *                  (exact year of production and having at least the provided rating) within the
   *                  subtree rooted at current
   * @param current   "root" of the subtree we are looking for a match to find within it.
   */
  protected void lookupHelper(int year, double rating, BSTNode<Movie> current,
      ArrayList<Movie> movieList) {
    // TODO Complete the implementation of this method
    
    // TODO If the BST rooted at current is null, do nothing and return
    if (current==null) return;

    // TODO if the BST rooted at current is not empty, perform a pre-order traversal 
    // of the subtree starting from current looking for movies satisfying our search 
    // criteria, and add each of them to the movieList
    
    Movie c = current.getData();
    if (match(current.getData(), year, rating)) {
      movieList.add(current.getData());
    }
    lookupHelper(year, rating, current.getLeft(),  movieList);
    lookupHelper(year, rating, current.getRight(),  movieList);
  }
  
  private boolean match(Movie m, int year, double rating) {
    if (m.getYear()==year && m.getRating()>=rating) return true;
    return false;
  }
}