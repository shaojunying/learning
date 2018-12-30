using UnityEngine;

public class StartCubeScript : MonoBehaviour
{

	/*
	 * 用来指定旋转的速度
	 */
	public float rotateSpeed = 1f;

	/*
	 * 记录所有方块
	 */
	private GameObject[] cubes;
	private float t;
	private Vector3 rotateAxis = new Vector3(1,1,0);

	void Start()
	{
		cubes = GameObject.FindGameObjectsWithTag("Cube");
	}

	void Update()
	{
		t += Time.deltaTime* rotateSpeed;
		Vector3 rotate = rotateAxis * Mathf.Clamp(t,0,4) * 90;
		transform.eulerAngles = rotate;
		if (t >= 4f)
		{
			t = t-4;
		}
	}
}
