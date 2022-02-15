
# This program uses the Trefle REST API to request plant data from a database and store it as a JSON Object.
# Resource: https://trefle.io/

# The json package should come by default.
# To install the requests package, type "pip install requests" into the terminal.
# On thonnny search requests package and download

import requests
import json

def printAsFormattedJSON(jsonObject):
    """Prints JSON as a multi-line, indented string rather than a jumbled mess. 
       Truncates string that are more than 1,000 characters."""
    print(json.dumps(jsonObject, indent=2)[0:1000])

key = "Q3dROHP3_209RdzmVswm4TMoNHGPIeG59N_p0A1d5IQ"

#Woody Plants only excpt "Achillea-millefolium"
plantList = {"Sambucus-canadensis"}
for x in plantList:
    r = requests.get("https://trefle.io/api/v1/species/" + x + "?token=" + key)



    plant = r.json()["data"]

    scientificName = plant["scientific_name"]
    # The scientific name follows the Binomial nomenclature, and represents its genus and its species within the genus.
    
    print("Scientific Name: ", scientificName)
    commonName = plant["common_name"]
    # The usual common name, in english, of the species (if any).
    
    print("\""+scientificName, "Common Name\":"+"\""+str(commonName)+"\"")
    #imageData = plant["images"][""]
    #image = imageData[0]
    #imageUrl = image["image_url"]
    print("\""+scientificName, "URL\":")
    growthData = plant["growth"]
    spread = growthData["spread"]["cm"] 
    print("\""+scientificName, "Spread\":"+str(spread))   # The average spreading of the plant, in centimeters
    phMinimum = growthData["ph_minimum"]
    # The minimum acceptable soil pH (of the top 30 centimeters of soil) for the plant
    
    print("\""+scientificName, "Minimum ph\":"+"" +str(phMinimum)+"")
    phMaximum = growthData["ph_maximum"]
    # The maximum acceptable soil pH (of the top 30 centimeters of soil) for the plant
    
    print("\""+scientificName, "Maximum ph\":"+""+str(phMaximum)+"")
    light = growthData["light"]
    # Required amount of light, on a scale from 0 (no light, <= 10 lux) to 10 (very intensive insolation, >= 100 000 lux)
    
    print("\""+scientificName+ " Light\":"+ ""+str(light)+"")
    soil = growthData["soil_texture"]
    # Required texture of the soil, on a scale from 0 (clay) to 10 (rock)
    
    print("\""+scientificName, "Soil\":"+ ""+str(soil)+"")
    atmosphericHumidity = growthData["atmospheric_humidity"]
    # Required relative humidity in the air, on a scale from 0 (<=10%) to 10 (>= 90%)
    
    print("\""+scientificName, "Moisture\":"+""+str(atmosphericHumidity)+"")
    specificationData = plant["specifications"]
    averageHeight = specificationData["average_height"]["cm"]
    print("\""+ scientificName, "Average Height\":" + ""+str(averageHeight)+"")
    
    print("--------")
    
print(len(plantList)) 
#checks number of plants in list

