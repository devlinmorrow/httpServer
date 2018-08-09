package http.Responders.Handlers;

import http.Requesters.Request;
import http.Responders.FileContentConverter;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.util.ArrayList;

public class RangeResponder {

    private FileContentConverter fileContentConverter;

   public RangeResponder(FileContentConverter fileContentConverter) {
       this.fileContentConverter = fileContentConverter;
   }

    public Response performRangeRequest(Request request, byte[] fullContents) {
       Response response = new Response();
        String rangeSpec = request.getHeaders().get("Range");
        String rangeCut = rangeSpec.replaceAll("[^0-9-0-9]+", " ").trim();
        String rangePattern = findRangePattern(rangeCut);
        int first;
        int last;
        switch (rangePattern) {
            case "bothExist":
                String[] rangeNumbers = rangeCut.split("-");
                ArrayList<Integer> range = new ArrayList<>();
                for (String number : rangeNumbers) {
                    range.add(Integer.parseInt(number));
                }
                first = range.get(0);
                last = range.get(1);
                break;
            case "firstExists": {
                String singleNumber = rangeCut.replaceAll("-", "").trim();
                first = Integer.parseInt(singleNumber);
                last = fullContents.length - 1;
                break;
            }
            default: {
                String singleNumber = rangeCut.replaceAll("-", "").trim();
                int number = Integer.parseInt(singleNumber);
                first = fullContents.length - number;
                last = fullContents.length - 1;
                break;
            }
        }
        if (outOfRange(fullContents, first) || outOfRange(fullContents, last)) {
            response.setContentRangeHeader(("bytes " + "*/" + Integer.toString(fullContents.length)));
            response.setBodyContent(ResponseStatus.RANGENOTSATISFIABLE.getStatusBody());
            response.setStatus(ResponseStatus.RANGENOTSATISFIABLE);
        } else {
            byte[] specifiedContent = fileContentConverter.getPartialContent(fullContents, first, last);
            response.setBodyContent(specifiedContent);
            response.setContentRangeHeader(("bytes " + Integer.toString(first)
                + "-" + Integer.toString(last) + "/" + Integer.toString(fullContents.length)));
            response.setStatus(ResponseStatus.PARTIALCONTENT);
        }
        return response;
    }

    private boolean outOfRange(byte[] contents, int number) {
       return number > contents.length || number < 0;
    }

    private String findRangePattern(String rangeCut) {
       boolean firstExists = false;
       boolean lastExists = false;
        if (rangeCut.matches("[0-9]+-")) {
            firstExists = true;
        }
        if (rangeCut.matches("-[0-9]+")) {
            lastExists = true;
        }
        if (rangeCut.matches("[0-9]+-[0-9]+")) {
            return "bothExist";
        } else {
            if (firstExists) {
                return "firstExists";
            } else {
                return "lastExists";
            }
        }
    }
}
