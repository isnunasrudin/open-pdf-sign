package org.openpdfsign;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.EnumConverter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class SignatureParameters {
    @Parameter(required = false, names={"--image"}, description = "Image to be placed in signature block")
    @JsonProperty("image")
    private String imageFile;

    @Parameter(required = false, names={"--page"}, description = "Page where the signature block should be placed. [-1] for last page")
    @JsonProperty(value = "page")
    private Integer page;

    @Parameter(required = false, names={"--top"}, description = "Y coordinate of the signature block in cm")
    @JsonProperty("top")
    private float top = 1;

    @Parameter(required = false, names={"--left"}, description = "X coordinate of the signature block in cm")
    @JsonProperty("left")
    private float left = 1;

    @Parameter(required = false, names={"--width"}, description = "width of the signature block in cm")
    @JsonProperty("width")
    private float width = 10;

    @Parameter(required = false, names={"--hint"}, description = "text to be displayed in signature field")
    @JsonProperty("hint")
    private String hint;

    @Parameter(required = false, names={"--no-hint"}, description = "don't display a hint row")
    private Boolean noHint = false;

    @Parameter(required = false, names={"--timestamp"}, description = "include signed timestamp")
    @JsonProperty("timestamp")
    private Boolean useTimestamp = false;

    @Parameter(required = false, names={"--baseline-lt"}, description = "use PAdES profile with long-term validation material")
    private Boolean useLT = false;

    @Parameter(required = false, names={"--baseline-lta"}, description = "use PAdES profile with long term availability and integrity of validation material")
    private Boolean useLTA = false;

    @Parameter(required = false, names={"--label-hint"}, description = "label for the 'hint' row")
    private String labelHint;

    @Parameter(required = false, names={"--label-timestamp"}, description = "label for the 'timestamp' row")
    private String labelTimestamp;

    @Parameter(required = false, names={"--label-signee"}, description = "label for the 'signee' row")
    private String labelSignee;

    @Parameter(required = false, names={"--tsa"}, description = "use specific time stamping authority as source (if multiple given, will be used in given order as fallback)")
    @JsonProperty("tsa")
    private List<String> TSA = new LinkedList<>();

    @Parameter(required = false, names = {"--timezone"}, description = "use specific timezone for time info, e.g. Europe/Vienna")
    @JsonProperty("timezone")
    private String timezone;

    @Parameter(required = false, names={"--certification"}, converter = CertificationModeConverter.class, description = "Quality of signature certification (DocMDP) and allowed changes after signing")
    @JsonProperty("certification")
    private CertificationMode certification = CertificationMode.CERTIFIED_MINIMAL_CHANGES_PERMITTED;

    @Parameter(required = false, names={"--reason"}, description = "Reason for signing")
    @JsonProperty("reason")
    private String reason;

    @Parameter(required = false, names={"--location"}, description = "Location during signing")
    @JsonProperty("location")
    private String location;

    @Parameter(required = false, names={"--contact"}, description = "Contact information of the signer")
    @JsonProperty("contact")
    private String contact;

    public static enum CertificationMode {
        NOT_CERTIFIED("not-certified"),
        CERTIFIED_NO_CHANGE_PERMITTED("certified-no-change-permitted"),
        CERTIFIED_MINIMAL_CHANGES_PERMITTED("certified-minimal-changes-permitted"),
        CERTIFIED_CHANGES_PERMITTED("certified-changes-permitted");

        private String certification;

        CertificationMode(String certification) {
            this.certification = certification;
        }

        @JsonCreator
        public static CertificationMode forValue(String value) {
            for (CertificationMode mode : CertificationMode.values()) {
                if (mode.toString().equals(value)) {
                    return mode;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return certification;
        }
    }


    public static class CertificationModeConverter extends EnumConverter<CertificationMode> {

        public CertificationModeConverter(String optionName) {
            super(optionName, CertificationMode.class);
        }

        @Override
        public CertificationMode convert(String value) {
            //value needs to be enum
            CertificationMode mode = CertificationMode.forValue(value);
            if (mode != null) {
                return mode;
            }

            //call super class converter in order to get error message
            return super.convert(value);
        }
    }


}
