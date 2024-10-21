package net.xbookmark.common.util.matcher;

public interface PatternMatcher {
  /**
   * Returns <code>true</code> if the given <code>source</code> matches the specified <code>pattern
   * </code>, <code>false</code> otherwise.
   *
   * @param pattern the pattern to match against
   * @param source the source to match
   * @return <code>true</code> if the given <code>source</code> matches the specified <code>pattern
   *     </code>, <code>false</code> otherwise.
   */
  boolean matches(String pattern, String source);
}
