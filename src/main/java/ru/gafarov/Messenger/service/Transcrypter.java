package ru.gafarov.Messenger.service;

public interface Transcrypter {
    public String toCyrilic(String partOfName);
    public String toLatin(String partOfName);
}
