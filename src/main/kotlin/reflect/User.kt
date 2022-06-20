package reflect

data class UserDTO(val id: Int, val name: String, val age: Int, val group: Group) : DeepCopyable

data class UserVO(val id: Int, val name: String, val age: Int, val group: Group) : DeepCopyable

data class Group(val name: String, val id: Int) : DeepCopyable