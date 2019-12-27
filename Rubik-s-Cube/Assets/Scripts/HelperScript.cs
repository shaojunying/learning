using UnityEngine;

public class HelperScript : MonoBehaviour
{

	/*
	 * 只有Running为True才可以拖动鼠标,否则将不能拖动鼠标
	 */
	public static bool Running{ get; set; }
	/*
	 * 记录旋转轴
	 */
	public static Vector3 RotateAxis{ get; set; }
	
	/*
	 * 记录当前是否在旋转
	 */
	public static bool Rotating { get; set; }

	public static Vector3[] OriginAxises =
	{
		Vector3.right,
		Vector3.up,
		Vector3.down,
		Vector3.left,
		Vector3.forward,
		Vector3.back
	};
	
	/*
	 * 当前正在打乱魔方
	 */
	public static bool Shuffling { get; set; }
	/*
	 * 当前打乱魔方的剩余步数
	 */
	public static int Steps { get; set; }

	public static bool Reseting { get; set; }

}
