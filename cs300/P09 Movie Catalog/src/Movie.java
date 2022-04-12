
/**
 * This class models a movie
 * 
 * @author Sulong
 *
 */
public class Movie implements Comparable<Movie> {
  // Note: DO NOT MAKE ANY CHANGE TO THIS CLASS AND DO NOT SUBMIT IT TO GRADESCOPE

  private double rating; // users rating of this movie in the scale from 0.0 to 10.0
  private int year; // year of production of this movie
  private String name; // name of this movie

  /**
   * Creates a new Movie with given attributes
   * 
   * @param rating rating average of a movie by viewers
   * @param year   the year of production
   * @param name   name of the movie
   * @throws an IllegalArgumentException if year is less than 1900, if rating is out of the range
   *            from 0.0 to 10.0 or if name is null or an empty string
   */
  public Movie(int year, double rating, String name) {
    if (year < 1900)
      throw new IllegalArgumentException("Invalid year of production.");
    if (rating < 0.0 || rating > 10.0)
      throw new IllegalArgumentException("Invalid rate. The rate must be in the scale of 10.");
    if (name == null || name.length() == 0)
      throw new IllegalArgumentException("Invalid name");
    this.year = year;
    this.rating = rating;
    this.name = name;
  }

  /**
   * Gets the rating of this movie
   * 
   * @return the rating of movie
   */
  public double getRating() {
    return rating;
  }

  /**
   * Gets the year when this movie was produced
   *
   * @return the year of the production of this movie
   */
  public int getYear() {
    return year;
  }

  /**
   * Gets the name of this movie
   * 
   * @return the name of this movie
   */
  public String getName() {
    return name;
  }

  /**
   * Compares two movies for ordering with respect to their ratings, years, and names
   * 
   * @return 0 if otherMovie has the same year, same rate, and same name as this movie; an integer
   *         less then 0 if this movie is older than the otherMovie, or if they were produced in the
   *         same year but this movie has a lower rating, or if they have the same year and same
   *         rating but this movie has a lower lexical order than otherMovie; otherwise returns an
   *         integer greater than 0
   * 
   */
  @Override
  public int compareTo(Movie otherMovie) {
    if (this.year == otherMovie.year) {
      if (this.rating == otherMovie.rating) {
        return this.name.compareTo(otherMovie.name);
      } else if (this.rating < otherMovie.rating) {
        return -1;
      } else {
        return 1;
      }
    }
    return this.year - otherMovie.year;
  }

  /**
   * Checks whether this movie equals to the other movie
   * 
   * @param obj other object to compare @ return true if this movie is equal to the input argument
   * obj and false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    return (obj != null && obj instanceof Movie && compareTo((Movie) obj) == 0);
  }

  /**
   * Returns a String representation of the movie
   * 
   * @return the movie as a String in the format
   */
  public String toString() {
    return "[(Year: " + year + ") (Rate: " + rating + ") (Name: " + name + ")]";
  }

}