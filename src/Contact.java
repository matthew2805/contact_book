import java.util.Arrays;

public class Contact {
    private String contactName;
    private final ContactInfo[] contactInfos = new ContactInfo[10];
    private boolean telFlag = false;

    {
        Email.count = 0;
        Social.count = 0;
        Email.pos = 1;
        Social.pos = 4;
    }

    private class NameContactInfo implements ContactInfo {
        public String getTitle() { return "Name"; }

        public String getValue() { return contactName; }
    }

    public static class Email implements ContactInfo {

        private String value;
        private static int count = 0;
        private static int pos = 1;

        {
            count++;
            pos++;
        }

        public static boolean isFull() { return count >= 3; }

        public static int getPos() { return pos; }

        public String getTitle() { return "Email"; }

        public final void setValue(String value) { this.value = value; }

        public String getValue() { return value; }

    }

    public static class Social implements ContactInfo {
        private String contactSocial, contactIdSocial;
        private static int count = 0;
        private static int pos = 4;

        {
            count++;
            pos++;
        }

        public final void setSocialValues(String contactSocial, String contactIdSocial) {
            this.contactSocial = contactSocial;
            this.contactIdSocial = contactIdSocial;
        }

        public static boolean isFull() { return count >= 5; }

        public String getTitle() { return contactSocial; }

        public static int getPos() { return pos; }

        public String getValue() { return contactIdSocial; }
    }

    public Contact(String contactName) {
        this.contactName = contactName;
        contactInfos[0] = new NameContactInfo();
    }

    public void rename(String newName) {
        if (newName == null || newName.equals("")) return;
        contactName = newName;
        contactInfos[0] = new NameContactInfo();
    }

    public Email addEmail(String localPart, String domain) {
        if (Email.isFull()) return null;
        Email email = new Email();
        email.setValue(String.format("%s@%s", localPart, domain));
        contactInfos[Email.getPos()] = email;
        return email;
    }


    public Email addEpamEmail(String firstname, String lastname) {
        if (Email.isFull()) return null;
        Email email = new Email() {
            @Override
            public String getTitle() { return "Epam Email"; }

            @Override
            public String getValue() { return String.format("%s_%s@epam.com", firstname, lastname); }
        };
        contactInfos[Email.getPos()] = email;
        return email;
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if (telFlag) return null;
        ContactInfo phoneNumber = new NameContactInfo() {
            @Override
            public String getTitle() { return "Tel"; }

            @Override
            public String getValue() { return String.format("+%d %s", code, number); }
        };
        telFlag = true;
        contactInfos[1] = phoneNumber;
        return phoneNumber;
    }

    public Social addTwitter(String twitterId) {
        return addSocialMedia("Twitter", twitterId);
    }

    public Social addInstagram(String instagramId) {
        return addSocialMedia("Instagram", instagramId);
    }

    public Social addSocialMedia(String title, String id) {
        if (Social.isFull()) return null;
        Social social = new Social();
        social.setSocialValues(title, id);
        contactInfos[Social.getPos()] = social;
        return social;
    }

    public ContactInfo[] getInfo() {
        ContactInfo[] temp = contactInfos.clone();
        int i = 0;
        for (int n = 0; n < temp.length; n++) {
            if (temp[n] == null) continue;
            temp[i++] = temp[n];
        }
        return Arrays.copyOf(temp, i);
    }

}
