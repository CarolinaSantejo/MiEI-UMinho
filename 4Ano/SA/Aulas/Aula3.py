import requests, json
from Adafruit_IO import Client

# Adafruit data
username = 'carolinaSantejo'
key = 'aio_DqXa30o4P1DSuPmL9o78J9pUBSl9'
feedName = 'tempdata'

aio = Client(username, key)

# API data
apikey = "323137feb66d2a26089b09332f787ebf"

lat = 42.0788
lon = -8.48013 

url = f'http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apikey}'

response = requests.get(url).text
data = json.loads(response)

temperature = data["main"]["temp"] - 273.15

print('Uploading data...')
aio.send(feedName, temperature)
print('Upload sucessfull!')

print(temperature)