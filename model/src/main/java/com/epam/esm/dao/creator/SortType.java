package com.epam.esm.dao.creator;

public enum SortType {

    /**
     * Ascending sort.
     */
    ASC("ASC"),

    /**
     * Descending sort.
     */
    DESC("DESC");

    private final String sortTypeName;

    SortType(String sortTypeName) {
        this.sortTypeName = sortTypeName;
    }

    public String getSortTypeName() {
        return sortTypeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(sortTypeName);
        return sb.toString();
    }
}
