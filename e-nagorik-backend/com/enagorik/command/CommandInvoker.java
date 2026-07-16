package com.enagorik.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Command pattern invoker: decouples the Officer/Mayor desk UI from the
 * receiver classes and keeps history so any one-click action (FR-7) can
 * be undone.
 */
public class CommandInvoker
{
    private final Deque<ApplicationCommand> history = new ArrayDeque<>();

    public void run(ApplicationCommand command)
    {
        command.execute();
        history.push(command);
    }

    public void undoLast()
    {
        if (!history.isEmpty())
        {
            history.pop().undo();
        }
    }
}