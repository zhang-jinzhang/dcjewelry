package com.ceyi.project.dcjewelry.picture;

public class NewImage {

	private String path;
	
	private String url;
	
	private String filename;
	
	private String ext;
	public NewImage(String path, String url, String filename, String ext) {
		super();
		this.path = path;
		this.url = url;
		this.filename = filename;
		this.ext = ext;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public NewImage(String path, String url) {
		super();
		this.path = path;
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
