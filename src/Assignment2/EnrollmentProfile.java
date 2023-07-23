public class EnrollmentProfile {
    private String userId;
    private String firstName;
    private String lastName;
    private int version;
    private String insuranceCompany;
    private String profileId;

    //Constructor
    private EnrollmentProfile(final Builder builder) {
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.version = builder.version;
        this.insuranceCompany = builder.insuranceCompany;

        //This will be the identifier we use to differentiate the EnrollmentIds
        //This allows for multiple companies to use the same ID for different users.
        this.profileId = this.insuranceCompany + "-" + this.userId;
    }

    //Getters
    public String getUserId() {
        return this.userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getVersion() {
        return this.version;
    }

    public String getInsuranceCompany() {
        return this.insuranceCompany;
    }

    public String getProfileId() {
        return this.profileId;
    }

    /**
     * We'll use this to cleanly get all of the values in a .csv-friendly format when we're writing to the files.
     * 
     * @return Comma separated String containing userId, firstName, lastName, version, and insuranceCompany
     */
    public String getAsString() {
        return String.join(",", this.userId, this.firstName, this.lastName, String.valueOf(this.version), this.insuranceCompany);
    }

    /**
     * Construct EnrollmentProfile objects with this fluent builder
     */
    public static class Builder {
        private String userId;
        private String firstName;
        private String lastName;
        private int version;
        private String insuranceCompany;

        public Builder() {
        }

        public Builder setId(final String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setVersion(final int version) {
            this.version = version;
            return this;
        }

        public Builder setInsuranceCompany(final String insuranceCompany) {
            this.insuranceCompany = insuranceCompany;
            return this;
        }

        public EnrollmentProfile build() {
            return new EnrollmentProfile(this);
        }
    }
}
