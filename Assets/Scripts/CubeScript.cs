using System;
using UnityEngine;
using Random = System.Random;

public class CubeScript : MonoBehaviour
{
	/*
	 * 这里可以考虑初始的时候将每个魔方放在正确的位置上
	 * 之后遍历所有的元素,如果对应的元素和
	 */
	
	
	/*
	 * 默认所有的魔方元素均属于该元素的子元素
	 * 在旋转的时候发生旋转的物体将绑定在另外的物体上,
	 * 待旋转结束之后恢复为该物体
	 */
	public GameObject MagicCube;
	/*
	 * 用来指定旋转的速度
	 */
	public float rotateSpeed = 2f;
	
	/*
	 * 点击的时候点击的物体
	 * 旋转的时候所有要旋转的物体都以该物体为父对象
	 */
	private GameObject pressedObject;
	/*
	 * 鼠标点击的面的法线
	 */
	private Vector3 normal;
	/*
	 * 鼠标点击的起始点
	 */
	private Vector3 mouseStart;
	/*
	 * 鼠标拖动的时候的点(移动距离大于0.2才算拖动)
	 */
	private Vector3 mouseRun;
	/*
	 * 记录叉乘结果(用来计算旋转轴)
	 */
	private Vector3 mouseCross;
	/*
	 * 当前旋转的角度
	 */
	private float t;
	/*
	 * 记录所有方块
	 * 这里按照z,y,x的顺序讲所有的魔方块存入cubes中
	 */
	private GameObject[] cubes=new GameObject[26];
	private Random random;
	
	void Start()
	{
		random = new Random();
		storeAllCubesOrdered();
		HelperScript.Running = true;
	}

	void Update()
	{
		/*
		 * 左键按下,记点击点为起始点
		 */
		if (Input.GetMouseButtonDown(0) && HelperScript.Running)
		{
			Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
			RaycastHit hitStart;
			if (Physics.Raycast(ray.origin, ray.direction, out hitStart))
			{
				pressedObject = hitStart.transform.gameObject;
				normal = hitStart.normal;
				mouseStart = hitStart.point;
			}
		}

		/*
		 * 鼠标正在按下并拖动
		 */
		if (Input.GetMouseButton(0) &&HelperScript.Running && pressedObject != null)
		{
			Ray rayRun = Camera.main.ScreenPointToRay(Input.mousePosition);
			RaycastHit hitRun;
			if (Physics.Raycast(rayRun.origin, rayRun.direction, out hitRun))
			{
				mouseRun = hitRun.point - mouseStart;
				if (mouseRun.sqrMagnitude > 350f)
				{
					mouseCross = Vector3.Cross(normal, mouseRun).normalized;
					// 将计算出的旋转轴与各个轴比较,得到旋转轴
					RotatePoint(mouseCross);
					pressedObject = null;
				}
			}
		}

		/*
		 * 当前魔方正在被打乱
		 */
		if (HelperScript.Shuffling&&!HelperScript.Rotating)
		{
			/*
			 * 打乱的过程中不能拖动鼠标
			 */
			HelperScript.Running = false;
			if (HelperScript.Steps == 0)
			{
				HelperScript.Running = true;
				HelperScript.Shuffling = false;
				return;
			}
			Random random = new Random();
			pressedObject = cubes[random.Next(0, cubes.Length)];
			mouseCross = HelperScript.OriginAxises[random.Next(0,HelperScript.OriginAxises.Length)];
			RotatePoint(mouseCross);
			HelperScript.Steps--;
		}

		/*
		 * 当前魔方正在旋转
		 */
		if (HelperScript.Rotating)
		{
			t += Time.deltaTime * rotateSpeed;
			Vector3 rotate = HelperScript.RotateAxis * Mathf.Clamp01(t) * 90;
			transform.eulerAngles = rotate;
			if (t >= 1f)
			{
				t = 0;
				foreach (GameObject cube in cubes)
				{
					cube.transform.parent = MagicCube.transform;
					cube.transform.localScale = Vector3.one;
				}
				transform.rotation = Quaternion.identity;
				HelperScript.Rotating = false;
				HelperScript.Running = true;
				/*
				 * 此处需要修改
				 */
			}
		}
		
		/*
		 * 恢复魔方
		 */
		if (HelperScript.Reseting)
		{
			for (int i=0;i<cubes.Length;i++)
			{
				GameObject cube = cubes[i];
				cube.transform.localRotation = Quaternion.Euler(0, 0, 0);
				int trueIndex = i / 13==0?i:i+1;
				int x = trueIndex / 9 - 1;
				int y = trueIndex % 9 / 3 - 1;
				int z = trueIndex % 3 - 1;
				cube.transform.localPosition = new Vector3(x,y,z);
			}

			HelperScript.Reseting = false;
		}
	}

	/*
	 * 遍历六个轴找到旋转轴
	 */
	void RotatePoint(Vector3 mouseCross)
	{
		for (int i = 0; i < HelperScript.OriginAxises.Length; i++)
		{
			Vector3 vector3 = HelperScript.OriginAxises[i];
			// 只有点乘之后在指定范围才说明以此轴为旋转轴
			float dot = Vector3.Dot(vector3, mouseCross);
			if (dot > 0.72f && dot <= 1)
			{
				HelperScript.RotateAxis = vector3;
				for (int j = 0; j < 3; j++)
				{
					if (Math.Abs(Mathf.Abs(vector3[j]) - 1) < 0.1f)
					{
						/*
						 * 将同一层物体绑定到当前物体上,进行旋转
						 */
						FindFather(j);
					}
				}
			}
		}
	}

	/*
	 * 找到转动轴之后将与点击物体同一层的物体父对象指定为初始点击的物体
	 * 用该物体带动该层的旋转
	 */
	private void FindFather(int point)
	{
		// 取出选定物体投影到旋转轴上的坐标
		float pressedObjectPoint = pressedObject.transform.localPosition[point];
		
		/*
		 * 在进行随机旋转的时候,需要根据旋转轴来随机选区旋转面
		 */
		if (HelperScript.Shuffling)
		{
			pressedObjectPoint = random.Next(-1,2);
		}
		
		/*
		 * 将旋转面上的所有GameObject对象绑定到空对象上便于操作
		 */
		foreach (GameObject cube in cubes)
		{
			// print(cube.transform.position);
			float cubePoint = cube.transform.localPosition[point];
			if (Math.Abs(pressedObjectPoint - cubePoint) < 0.1f)
			{
				cube.transform.parent = gameObject.transform;
			}
		}
		HelperScript.Running = false;
		HelperScript.Rotating = true;
	}
	
	/*
	 * 将所有cube元素按照z,x,y的依次增加的顺序存入cubes中,
	 * 之后可以直接通过计算该地的坐标判断
	 */
	private void storeAllCubesOrdered()
	{
		GameObject[] tempCubes =GameObject.FindGameObjectsWithTag("Cube");
		for (int i = 0; i < tempCubes.Length; i++)
		{
			// 首先根据坐标计算出元素的下标
			Vector3 position = tempCubes[i].transform.localPosition;
			int computeIndex = (int)((position.x + 1) * 9 + (position.y + 1) * 3 + position.z+1+0.5);
			int trueIndex;
			if (computeIndex / 13 == 0)
			{
				trueIndex = computeIndex;
			}
			else
			{
				trueIndex = computeIndex - 1;
			}
			tempCubes[i].name = trueIndex.ToString();
			cubes[trueIndex] = tempCubes[i];
		}
	}
}
