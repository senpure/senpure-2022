package com.senpure.io.generator.util;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * NoteUtil
 *
 * @author senpure
 * @time 2018-11-21 11:10:33
 */
public class NoteUtil {

    private static String REGEDIT_NOTEPAD_PLUS_PLUS = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Notepad++";


    public static void openNote(File file, int line) {

        try {
            Runtime run = Runtime.getRuntime();
            run.exec(findNotepadPath() + " " + file.getAbsolutePath());
            Thread.sleep(200);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_END);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_END);
            for (int i = 0; i < line; i++) {
                robot.keyPress(KeyEvent.VK_UP);
                robot.keyRelease(KeyEvent.VK_UP);
            }

            // process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static String findNotepadPath() {
        List<String> infos = queryRegedit(REGEDIT_NOTEPAD_PLUS_PLUS);
        String path = null;
        for (String info : infos) {
            info = info.trim();
            if (info.startsWith("DisplayIcon")) {
                info = info.replace("DisplayIcon", "");
                info = info.replace("REG_SZ", "");
                info = info.trim();
                path = info;
                break;
            }
        }
        if (path == null) {
            path = "notepad.exe";
        }
        return path;
    }

    public static List<String> queryRegedit(String key) {
        List<String> infos = new ArrayList<>();
        //装饰冒号
        if (!key.startsWith("\"")) {
            key = "\"" + key + "\"";
        }
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        BufferedReader br = null;
        try {
            process = runtime.exec("cmd /c reg query " + key);
            br = new BufferedReader(new InputStreamReader(process
                    .getInputStream(), "GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    infos.add(line);
                }
                // System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            process.destroy();
        }
        return infos;
    }

    public static void main(String[] args) throws Exception {


    }
}
