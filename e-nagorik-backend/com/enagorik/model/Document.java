package com.enagorik.model;

import java.io.Serializable;

public class Document implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int documentId;
    private final String fileName;
    private final String fileType;
    private final String filePath;
    private boolean verified;

    public Document(int documentId, String fileName, String fileType, String filePath)
    {
        this.documentId = documentId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.verified = false;
    }

    public boolean verify()
    {
        this.verified = true;
        return verified;
    }

    public int getDocumentId()
    {
        return documentId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public boolean isVerified()
    {
        return verified;
    }
}