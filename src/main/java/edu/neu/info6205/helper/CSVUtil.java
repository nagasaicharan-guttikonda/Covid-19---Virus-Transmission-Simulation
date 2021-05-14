package edu.neu.info6205.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * read and write CSV
 *
 * @author Joseph Yuanhao Li
 * @date 4/11/21 03:46
 */
public class CSVUtil {
    /**
     * read
     *
     * @param file     file path(auto create if not exist)
     * @param dataList data
     * @return
     */


    public static boolean exportCsv(File file, List<String> dataList) {
        boolean isSucess = false;

        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw = new BufferedWriter(osw);
            if (dataList != null && !dataList.isEmpty()) {
                for (String data : dataList) {
                    bw.append(data).append("\r");
                }
            }
            isSucess = true;
        } catch (Exception e) {
            isSucess = false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                    bw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSucess;
    }

    /**
     * write
     *
     * @param file file path
     * @return
     */


    public static List<String> importCsv(File file) {
        List<String> dataList = new ArrayList<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dataList;
    }


    /**
     * CSV read test
     *
     * @throws Exception
     */


    public static void importCsv() {
        List<String> dataList = CSVUtil.importCsv(new File("/Users/yamato/Downloads/java-test.csv"));
        if (dataList != null && !dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                String s = dataList.get(i);
                System.out.println(s);
//                String[] as = s.split(",");
//                System.out.println(as[0]);
//                System.out.println(as[1]);
//                System.out.println(as[2]);
            }
        }
    }

    /**
     * CSV Write Test
     *
     * @throws Exception
     */

    public static void exportCsv() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("Days,Susceptible,Exposed,Infected, Removed");
        dataList.add("1,10000,0,10,0");

        String path = "/Users/yamato/Downloads/java-test.csv";
        boolean isSuccess = CSVUtil.exportCsv(new File(path), dataList);
        System.out.println("Write CSV Success: " + path);
    }


    public static void main(String[] args) {
        exportCsv();
        importCsv();
    }
}
