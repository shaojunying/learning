using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraScript : MonoBehaviour
{

	/*
	 * 存储相机对象
	 */
	public Transform cameraTarget;
	/*
	 * 水平滑动速度
	 */
	public float horizontalSpeed = 2.0f;
	/*
	 * 竖直滑动速度
	 */
	public float verticalSpeed = 2.0f;
	/*
	 * 滚轮滚动速度
	 */
	public float scrollSpeed = 2.0f;
	/*
	 * 
	 */
	public float lerpSpeed = 5.0f;
	private float h = 0;
	private float v = 0;
	private float caremaDistance;
		
	
	// Use this for initialization
	void Start () {
		Vector3 localRotation = transform.eulerAngles;
		v = localRotation.x;
		h = localRotation.y;	
	}
	
	// Update is called once per frame
	void Update() {
		if(Input.GetMouseButton(1)){
			h += horizontalSpeed * Input.GetAxis("Mouse X");
			v -= verticalSpeed * Input.GetAxis("Mouse Y");
			v = Mathf.Clamp (v,-50,50);
		}
		Quaternion rotationTo = Quaternion.Euler(v, h, 0);
		transform.rotation = Quaternion.Lerp(transform.rotation,rotationTo,Time.deltaTime * lerpSpeed);
		
		caremaDistance += Input.GetAxis("Mouse ScrollWheel") * scrollSpeed ;
		caremaDistance = Mathf.Clamp(caremaDistance,-4,1);
		cameraTarget.localPosition = Vector3.Lerp(cameraTarget.localPosition,new Vector3(0,0,caremaDistance - 3),Time.deltaTime * lerpSpeed);
	}
}
