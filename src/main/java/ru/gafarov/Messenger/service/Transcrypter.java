package ru.gafarov.Messenger.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Transcrypter {
    private final String[] cyrilic = {"а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"};
    private final String[] latin = {"a", "b", "v", "g", "d", "e", "jo", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "c", "ch", "sh", "shh", "", "y", "\'", "e", "ju", "ja"};
    private final List<String> cyriilcList = Arrays.asList(cyrilic);
    private final List<String> latinList = Arrays.asList(latin);

    public String toCyrilic(String partOfName) {
        String partOfNameLowerCase = partOfName.toLowerCase();
        int i = 0;
        StringBuilder partOfNameCyrilicLowerCase = new StringBuilder("");
        while (i < partOfNameLowerCase.length()) {
            String threeSymbols = "";
            String twoSymbols = "";
            String oneSymbol = partOfNameLowerCase.substring(i, i + 1);
            if (partOfNameLowerCase.substring(i).length() > 2) {
                threeSymbols = partOfNameLowerCase.substring(i, i + 3);
            }
            if (partOfNameLowerCase.substring(i).length() > 1) {
                twoSymbols = partOfNameLowerCase.substring(i, i + 2);
            }
            if (threeSymbols.equals("shh")) {
                partOfNameCyrilicLowerCase.append("щ");
                i += 3;
            } else {
                switch (twoSymbols) {
                    case "jo": partOfNameCyrilicLowerCase.append("ё"); i += 2; break;
                    case "zh": partOfNameCyrilicLowerCase.append("ж"); i += 2; break;
                    case "ch": partOfNameCyrilicLowerCase.append("ч"); i += 2; break;
                    case "sh": partOfNameCyrilicLowerCase.append("ш"); i += 2; break;
                    case "ju": partOfNameCyrilicLowerCase.append("ю"); i += 2; break;
                    case "ja": partOfNameCyrilicLowerCase.append("я"); i += 2; break;
                    default:
                        if (latinList.indexOf(oneSymbol)>= 0){
                            partOfNameCyrilicLowerCase.append(cyrilic[latinList.indexOf(oneSymbol)]);
                        } else {
                            partOfNameCyrilicLowerCase.append(oneSymbol);
                        }
                        i++;
                        break;
                }
            }
        }

        return partOfNameCyrilicLowerCase.toString();
    }


    public String toLatin(String partOfName) {
        String partOfNameLowerCase = partOfName.toLowerCase();
        int i = 0;
        StringBuilder partOfNameLatinLowerCase = new StringBuilder("");
        while (i < partOfNameLowerCase.length()) {
            String oneSymbol = partOfNameLowerCase.substring(i, i + 1);

            if (cyriilcList.indexOf(oneSymbol) >= 0) {
                partOfNameLatinLowerCase.append(latin[cyriilcList.indexOf(oneSymbol)]);
            } else {
                partOfNameLatinLowerCase.append(oneSymbol);
            }
            i++;
        }
        return partOfNameLatinLowerCase.toString();
    }
    public String trimSoftAndHardSymbol(String str){
        return str.replace("ь","").replace("\'","").replace("ъ","");
    }
}
