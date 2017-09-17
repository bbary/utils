/**
  * Created by brahim on 9/17/17.
  */
class Position (var x: Int, var y: Int, var orientation: orientationEnum) {

  /**
    * kernel logic of the application: take the move and return next new position
    * @param move
    * @return new position
    */
  def move(move: moveEnum, xMax: Int, yMax: Int): Position ={
      move match {
        case moveEnum.D => orientation = orientation.rotateD
        case moveEnum.G => orientation = orientation.rotateG
        case moveEnum.A =>
          orientation match {
            case orientationEnum.E => if (x < xMax) x += 1
            case orientationEnum.W => if (x > 0) x -= 1
            case orientationEnum.N => if (y < yMax) y += 1
            case orientationEnum.S => if (y > 0) y -= 1

          }
      }
      this
      }

  /**
    * display custom message
    * @return
    */
  override def toString: String = s"$x $y $orientation"
}



