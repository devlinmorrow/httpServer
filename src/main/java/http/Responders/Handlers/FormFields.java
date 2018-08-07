package http.Responders.Handlers;

import java.util.Map;

public class FormFields {

    private Map<String, String> formFields;

    public FormFields(Map<String, String> formFields) {
        this.formFields = formFields;
    }

    public Map<String, String> getFormFields() {
        return formFields;
    }
}
