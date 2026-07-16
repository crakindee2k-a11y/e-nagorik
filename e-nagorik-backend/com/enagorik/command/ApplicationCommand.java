package com.enagorik.command;

public interface ApplicationCommand
{
    void execute();
    void undo();
}