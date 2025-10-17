package com.poultix.server.constants;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LocationConstants {
    
    public static final List<String> PROVINCES = Arrays.asList(
        "KIGALI",
        "EASTERN",
        "WESTERN", 
        "NORTHERN",
        "SOUTHERN" 
    );
    
    public static final Map<String, List<String>> DISTRICTS = Map.of(
        "KIGALI", Arrays.asList("GASABO", "KICUKIRO", "NYARUGENGE"),
        "EASTERN", Arrays.asList("BUGESERA", "GATSIBO", "KAYONZA", "KIREHE", "NGOMA", "NYAGATARE", "RWAMAGANA"),
        "WESTERN", Arrays.asList("KARONGI", "NGORORERO", "NYABIHU", "NYAMASHEKE", "RUBAVU", "RUSIZI", "RUTSIRO"),
        "NORTHERN", Arrays.asList("BURERA", "GAKENKE", "GICUMBI", "MUSANZE", "RULINDO"),
        "SOUTHERN", Arrays.asList("GISAGARA", "HUYE", "KAMONYI", "MUHANGA", "NYAMAGABE", "NYANZA", "NYARUGURU", "RUHANGO")
    );
    
    // Document types required for pharmacy registration
    public static final List<String> REQUIRED_DOCUMENTS = Arrays.asList(
        "BUSINESS_LICENSE",
        "PHARMACIST_LICENSE", 
        "PREMISES_INSPECTION_REPORT",
        "TAX_CLEARANCE_CERTIFICATE",
        "INSURANCE_CERTIFICATE",
        "COMPLIANCE_CERTIFICATE",
        "PHARMACIST_PHOTO_ID"
    );
    
    // Document type display names
    public static final Map<String, String> DOCUMENT_DISPLAY_NAMES = Map.of(
        "BUSINESS_LICENSE", "Business License from RFDA",
        "PHARMACIST_LICENSE", "Pharmacist Professional License",
        "PREMISES_INSPECTION_REPORT", "Premises Inspection Report",
        "TAX_CLEARANCE_CERTIFICATE", "Tax Clearance Certificate from RRA",
        "INSURANCE_CERTIFICATE", "Insurance Certificate",
        "COMPLIANCE_CERTIFICATE", "Compliance Certificate",
        "PHARMACIST_PHOTO_ID", "Pharmacist Photo ID/Passport"
    );
}
