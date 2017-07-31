function AlbumAPI() {}

AlbumAPI.URL = APIConfig.ENDPOINT;

AlbumAPI.save = function(id, name, description, callback) {
    var endpoint = AlbumAPI.URL + "album/";
    var albumId = null;
    var requestMethod = "POST";
    if(id != null) {
        albumId = id;
        method = "PUT";
    }
	var settings = {
        url: endpoint,
        data: "{\"albumId\":\""+id+"\",\"name\":\""+name+"\",\"description\":\""+description+"\"}",
        method: requestMethod,
        dataType: "json",
        contentType: "application/json",
        processData: false,
        async: true
    };

    return Promise.resolve($.ajax(settings));
};

AlbumAPI.addPhoto = function(albumId, photoId) {
    var endpoint = AlbumAPI.URL + "album/addPhoto/" + albumId + "/" + photoId;
    var requestMethod = "PUT";
	var settings = {
        url: endpoint,
        method: requestMethod,
        dataType: "json",
        contentType: "application/json",
        async: true
    };

    return Promise.resolve($.ajax(settings));
};

AlbumAPI.removePhoto = function(albumId, photoId) {
    var endpoint = AlbumAPI.URL + "album/removePhoto/" + albumId + "/" + photoId;
    var requestMethod = "PUT";
	var settings = {
        url: endpoint,
        method: requestMethod,
        dataType: "json",
        contentType: "application/json",
        async: true
    };

    return Promise.resolve($.ajax(settings));
};

AlbumAPI.delete = function(albumId) {
    var endpoint = AlbumAPI.URL + "album/"+albumId;
	var settings = {url: endpoint, method: "DELETE", async: true};
    return Promise.resolve($.ajax(settings));
};

AlbumAPI.get = function(albumId) {
    var endpoint = AlbumAPI.URL + "album/"+albumId;
	var settings = {
        url: endpoint,
        dataType: "json",
        contentType: "application/json",
        async: true
    };
    return Promise.resolve($.ajax(settings));
};

AlbumAPI.getAll = function() {
    var endpoint = AlbumAPI.URL + "album";
	var settings = {
        url: endpoint,
        dataType: "json",
        contentType: "application/json",
        async: true
    };
    return Promise.resolve($.ajax(settings));
};
