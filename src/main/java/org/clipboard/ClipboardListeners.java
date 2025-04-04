package org.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class ClipboardListeners extends Thread implements ClipboardOwner {

    public interface EntryListener {
        void onCopy(String data);
    }

    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private EntryListener entryListener;

    public void setEntryListener(EntryListener entryListener) {
        this.entryListener = entryListener;
    }

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public void lostOwnership(Clipboard c, Transferable t) {
        try {
            sleep(200);
        } catch (Exception e) {
        }

        Transferable contents = clipboard.getContents(this);
        processContents(contents);
        regainOwnership(contents);
    }

    @SuppressWarnings("UseSpecificCatch")
    public void processContents(Transferable t){
        try {
            Transferable clipdata = clipboard.getContents(this);
            String what = (String) (clipdata.getTransferData(DataFlavor.stringFlavor));

            if (entryListener != null) {
                entryListener.onCopy(what);
            }
        } catch (Exception e) {
        }
    }

    public void regainOwnership(Transferable t) {
        clipboard.setContents(t, this);
    }

    @SuppressWarnings({"override", "empty-statement"})
    public void run() {
        Transferable transferable = clipboard.getContents(this);
        regainOwnership(transferable);

        while (true);
    }
}