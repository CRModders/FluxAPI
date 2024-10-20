package dev.crmodders.flux.impl.util;

public final class Strings {
    public static boolean endsWithIgnoreCase(final String self, final String rhs) {
        return self.regionMatches(true, self.length() - rhs.length(), rhs, 0, rhs.length());
    }

    private Strings() {}
}
