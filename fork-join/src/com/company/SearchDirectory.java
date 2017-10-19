package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class SearchDirectory extends RecursiveTask<List<String>> {

    private static final long serialVersionUID = 7570286838840187368L;

    private String directoryName = "";

    private String searchString = "";

    public SearchDirectory(String directoryName, String searchString)
    {
        this.directoryName = directoryName;
        this.searchString = searchString;
    }

    @Override
    protected List<String> compute(){

        List<String> matchingFilesList = new ArrayList<>();
        List<SearchDirectory> taskList = new ArrayList<>();


        File directory = new File(directoryName);


        if(directoryName == null || "".equals(directoryName) || !directory.exists())
            throw new IllegalArgumentException("Directory Name is NOT Valid");

        Optional<File[]> fileArray = Optional.ofNullable(directory.listFiles());

        if (fileArray.isPresent()) {




            taskList = Arrays.stream(fileArray.get())
                                .filter(File::isDirectory)
                                .map(file -> {
                                    SearchDirectory searchDirectory = new SearchDirectory(file.getPath(), searchString);
                                    searchDirectory.fork();
                                    return searchDirectory;
                                })
                                .collect(Collectors.toList());


            matchingFilesList = Arrays.stream(fileArray.get())
                                        .filter(f -> !f.isDirectory())
                                        .filter(f -> checkName(f.getName()))
                                        .map(File::getPath)
                                        .collect(Collectors.toList());




/*            for(File file : fileArray.get()) {

                if(file.isDirectory()) {

                    SearchDirectory searchDirectory = new SearchDirectory(file.getPath() ,searchString);
                    searchDirectory.fork();
                    taskList.add(searchDirectory);

                } else {

                    if(checkName(file.getName())) {
                        matchingFilesList.add(file.getPath());
                    }
                }
            }*/
        }

        for(SearchDirectory sd : taskList) {

            List<String> intermediateResultList = sd.join();
            matchingFilesList.addAll(intermediateResultList);
        }

        return matchingFilesList;
    }


    private boolean checkName(String filename) {
        return filename.contains(searchString);
    }


}
