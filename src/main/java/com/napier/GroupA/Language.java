package com.napier.GroupA;

/**
 *Represents a Language in the db.
 */
public class Language {

    /**
     *Code of Country the Language is Spoken in
     */
    public String language_country_code;

    /**
     *Name of Language
     */
    public String language_name;

    /**
     *Is the Language the Official Language of the Country
     */
    public boolean language_isOfficial;

    /**
     *Percentage of Country That Speaks the Language
     */
    public float language_percentage;
}
