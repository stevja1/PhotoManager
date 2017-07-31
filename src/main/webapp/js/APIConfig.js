function APIConfig() {};
//APIConfig.HOST = "http://localhost:8080/";
//APIConfig.HOST = "http://timetracker.jaredstevens.org/";
//APIConfig.HOST = "http://jaredstevens.org/";
APIConfig.HOST = "http://"+window.location.hostname+(window.location.port == "8080"?":8080/":"/");
APIConfig.ENDPOINT = APIConfig.HOST;