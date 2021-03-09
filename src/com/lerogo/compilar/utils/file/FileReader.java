package com.lerogo.compilar.utils.file;

import com.lerogo.compilar.utils.exception.file.ReadFileError;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lerogo
 * @date 2021/3/7 17:47
 * 传入fileName 然后得到一个list
 */
public class FileReader {
    /**
     * 要读取的文件名
     */
    private String fileName;

    /**
     * 读取出来的文件内容
     */
    private final List<String> readRow = new ArrayList<>();

    private int rowInd;


    /**
     * 读取出文件内容
     *
     * @param fileName
     * @throws ReadFileError
     */
    public FileReader(String fileName) throws ReadFileError {
        this.fileName = fileName;
        this.readFile2Row();
        this.rowInd = 0;
    }

    /**
     * 重新设置文件名后自动读入数据
     *
     * @param fileName
     * @throws ReadFileError
     */
    public void setFileName(String fileName) throws ReadFileError {
        this.fileName = fileName;
        this.readFile2Row();
        this.rowInd = 0;
    }

    public void setRowInd(int rowInd) {
        this.rowInd = rowInd;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getReadRow() {
        return readRow;
    }

    public int getRowInd() {
        return rowInd;
    }

    /**
     * @return 返回this.rowInd的代码
     */
    public String nextRow() {
        try {
            return this.readRow.get(this.rowInd++);
        } catch (IndexOutOfBoundsException e) {
            this.rowInd--;
            return null;
        }
    }

    /**
     * 传入ind 从0开始
     *
     * @param ind
     * @return 第ind行的代码
     */
    public String getIndRow(int ind) {
        try {
            return this.readRow.get(ind);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    private void readFile2Row() throws ReadFileError {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new java.io.FileReader(this.fileName));
            for (String tmpStr; (tmpStr = in.readLine()) != null; ) {
                this.readRow.add(tmpStr);
            }
        } catch (Exception e) {
            throw new ReadFileError();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
