package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {
        
        String[] data = inputLine.split(",");
        String stateName = data[2];
        StateNode cur = firstState;
        StateNode prev = null; 
        boolean found = false;
        
        while(cur != null) {
            if(cur.getName().equals(stateName)) {
                found = true;
                break;
            }
            prev = cur; 
            cur = cur.next;
        }
        if(!found) {
            StateNode newState = new StateNode(stateName,null,null);
            if(prev == null) {
                firstState = newState;
            }
            else {
                prev.setNext(newState);
            }
        }
    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {
        
        String[] data = inputLine.split(",");
        String countyName = data[1];
        String stateName = data[2];
        StateNode curState = firstState;
        
        while(curState != null && !curState.getName().equals(stateName)) {
            curState = curState.next;
        }
        if(curState != null) {
            CountyNode currentCounty = curState.getDown();
            CountyNode previousCounty = null;
            boolean found = false;
            
            while(currentCounty != null) {
                if(currentCounty.getName().equals(countyName)) {
                    found = true;
                    break;
                }
                previousCounty = currentCounty;
                currentCounty = currentCounty.next;
            }
        if(!found) {
            CountyNode newCounty = new CountyNode(countyName, null, null);
            if(previousCounty == null) {
                curState.setDown(newCounty); 
            } else {
                previousCounty.setNext(newCounty);
            }
        }
    }
    }


    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {
        
        String[] data = inputLine.split(",");
        String communityName = data[0];
        String countyName = data[1];
        String stateName = data[2];
        Data communityData = new Data(Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[8]), Double.parseDouble(data[9]), data[19], Double.parseDouble(data[49]), Double.parseDouble(data[37]), Double.parseDouble(data[121]));
        StateNode stateNode = firstState;
        
        while(stateNode != null && !stateNode.getName().equals(stateName)) {
        stateNode = stateNode.next;
    }
    if(stateNode != null) {
        CountyNode countyNode = stateNode.getDown();
        
        while(countyNode != null && !countyNode.getName().equals(countyName)) {
            countyNode = countyNode.next;
        }
        if(countyNode != null) {
            CommunityNode lastCommunityNode = countyNode.getDown();
            CommunityNode previousNode = null;
            boolean exists = false;
            
            while(lastCommunityNode != null) {
                if(lastCommunityNode.getName().equals(communityName)) {
                    exists = true;
                    break;
                }
                previousNode = lastCommunityNode;
                lastCommunityNode = lastCommunityNode.next;
            }
            if(!exists) {
                CommunityNode newCommunityNode = new CommunityNode(communityName, null , communityData);
                if(previousNode == null) {
                    countyNode.setDown(newCommunityNode);
                } 
                else {
                    previousNode.next = newCommunityNode;
                }
            }
        }
    }
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        StateNode curState = firstState;
        int count = 0;
        
        while(curState != null) {
            CountyNode currentCounty = curState.getDown(); 
            while(currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while(currentCommunity != null) {
                    Data dataObj = currentCommunity.getInfo();
                    double percentage = 0.0;
                    if("African American".equals(race)) {
                        percentage = dataObj.getPrcntAfricanAmerican() * 100;
                    } else if("Native American".equals(race)) {
                        percentage = dataObj.getPrcntNative() * 100;
                    } else if("Asian American".equals(race)) {
                        percentage = dataObj.getPrcntAsian() * 100;
                    } else if("White American".equals(race)) {
                        percentage = dataObj.getPrcntWhite() * 100;
                    } else if("Hispanic America".equals(race)) {
                        percentage = dataObj.getPrcntHispanic() * 100;
                    }
                    if(percentage >= userPrcntage && "True".equalsIgnoreCase(dataObj.getAdvantageStatus())) {
                        count++;
                    }
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.next;
            }
            curState = curState.next;
        }
        return count;
    }


    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities(double userPrcntage, String race) {
        
        StateNode curState = firstState;
        int count = 0;
        
        while(curState != null) {
            CountyNode currentCounty = curState.getDown();
            while(currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while(currentCommunity != null) {
                    Data dataObj = currentCommunity.getInfo();
                    double percentage = 0.0;
                    if("African American".equals(race)) {
                        percentage = dataObj.getPrcntAfricanAmerican() * 100;
                    } else if("Native American".equals(race)) {
                        percentage = dataObj.getPrcntNative() * 100;
                    } else if("Asian American".equals(race)) {
                        percentage = dataObj.getPrcntAsian() * 100;
                    } else if("White American".equals(race)) {
                        percentage = dataObj.getPrcntWhite() * 100;
                    } else if("Hispanic American".equals(race)) {
                        percentage = dataObj.getPrcntHispanic() * 100;
                    }
                    if(percentage >= userPrcntage && "False".equalsIgnoreCase(dataObj.getAdvantageStatus())) {
                        count++;
                    }
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.next;
            }
            curState = curState.next;
        }
        return count;
    }
    
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 


public ArrayList<StateNode> statesPMLevels(double PMlevel) {
    
    ArrayList<StateNode> qualifyingStates = new ArrayList<>();
    StateNode curState = firstState;
    
    while(curState != null) {
        boolean stateHasHighPM = false;
        CountyNode currentCounty = curState.getDown();
        while(currentCounty != null) {
            CommunityNode currentCommunity = currentCounty.getDown();
            while(currentCommunity != null) {
                if(currentCommunity.getInfo().getPMlevel() >= PMlevel) {
                    stateHasHighPM = true;
                    break;
                }
                currentCommunity = currentCommunity.getNext();
            }  
            if(stateHasHighPM) {
                break; 
            }
            currentCounty = currentCounty.next; 
        }
        if(stateHasHighPM) {
            qualifyingStates.add(curState);
        }
        curState = curState.next; 
    }
    return qualifyingStates; 
    }


    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood(double userPercntage) {
         
        StateNode curState = firstState;
        int count = 0;
        
        while(curState != null) {
            CountyNode currentCounty = curState.getDown();
            while(currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown(); 
                while(currentCommunity != null) {
                    double communityFloodChance = currentCommunity.getInfo().getChanceOfFlood();
                    if(communityFloodChance >= userPercntage) {
                        count++; 
                    }
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.next; 
            }
            curState = curState.next; 
        }
        return count; 
    }
    
    

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */


    public ArrayList<CommunityNode> lowestIncomeCommunities(String stateName) {
        
        ArrayList<CommunityNode> lowestIncomeCommunities = new ArrayList<>();
        StateNode curState = firstState;
        
        while(curState != null && !curState.getName().equals(stateName)) {
            curState = curState.getNext();
        }
        if(curState != null) {
            CountyNode currentCounty = curState.getDown();
            while(currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while(currentCommunity != null) {
                    if(lowestIncomeCommunities.size() < 10) {
                        lowestIncomeCommunities.add(currentCommunity);
                    } else {
                        double lowestPovertyLine = Double.MAX_VALUE;
                        int replaceIndex = -1;
                        for(int i = 0; i < lowestIncomeCommunities.size(); i++) {
                            CommunityNode testedCommunity = lowestIncomeCommunities.get(i);
                            if(testedCommunity.getInfo().getPercentPovertyLine() < lowestPovertyLine) {
                                lowestPovertyLine = testedCommunity.getInfo().getPercentPovertyLine();
                                replaceIndex = i;
                            }
                        }
                        if (currentCommunity.getInfo().getPercentPovertyLine() > lowestPovertyLine) {
                            if (replaceIndex != -1) {
                                lowestIncomeCommunities.set(replaceIndex, currentCommunity);
                            }
                        }
                    }
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
        }
        return lowestIncomeCommunities;
    }   
}
    
