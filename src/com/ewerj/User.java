package com.ewerj;

/*
Simple entity for holding user info
 */
public class User {
    private int id;
    private int numFiles;
    private int numBytes;

    public User(int id, int numFiles, int numBytes) {
        this.id = id;
        this.numFiles = numFiles;
        this.numBytes = numBytes;
    }

    public int getNumFiles(){
        return this.numFiles;
    }
    public int getNumBytes(){
        return this.numBytes;
    }

    @Override
    public String toString(){
        return String.format("User: %s", this.id);
    }
}
