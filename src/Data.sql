-- Khởi tạo và sử dụng cơ sở dữ liệu
create database if not exists erpcoffee;
use erpcoffee;

-- Tạo các bảng cho cơ sở dữ liệu
create table Roles (
	RolesId smallint zerofill auto_increment not null,
	RolesName varchar(100) not null,
	primary key (RolesId),
	unique (RolesName)
);

create table Functional (
	FunctionalId smallint zerofill auto_increment not null,
	FunctionalName varchar(100) not null,
	primary key (FunctionalId),
	unique (FunctionalName)
);

create table RoleFunction (
	RolesId smallint zerofill not null,
	FunctionalId smallint zerofill not null,
	primary key (RolesId, FunctionalId),
	foreign key (RolesId) references Roles(RolesId),
	foreign key (FunctionalId) references Functional(FunctionalId),
	unique (RolesId, FunctionalId)
);

create table Members
(
	PhoneNumber varchar(12) not null,
	FullName varchar(100) not null,
	Points int not null default 0 check (Points >= 0),
	primary key (PhoneNumber)
);

create table Sizes
(
	Sign varchar(4) not null,
	Description varchar(200) not null,
	primary key (Sign)
);

create table Ingredient
(
	IngredientId int zerofill auto_increment not null,
	IngredientName varchar(200) not null,
	IngredientType varchar(200) not null,
	IngredientPrice int not null check (IngredientPrice > 0),
	IngredientStorage double not null default 0 check (IngredientStorage >= 0),
	InventoryLimit double not null check (InventoryLimit >= 0),
	CreateAt date not null default current_date(),
	DeleteAt date default null,
	primary key (IngredientId),
	unique (IngredientName, IngredientType),
	unique (IngredientName)
);

create table WorkPosition
(
	PositionId smallint zerofill auto_increment not null,
	PositionName varchar(200) not null,
	PositionLevel smallint not null,
	primary key (PositionId),
	unique (PositionName)
);

create table Orders
(
	OrderId int zerofill auto_increment not null,
	TotalPrice int not null check (TotalPrice >= 0),
	OrderDate date not null default current_date(),
	PhoneNumber varchar(12) default null,
	primary key (OrderId),
	foreign key (PhoneNumber) references Members(PhoneNumber)
);

create table Category
(
	CategoryId smallint zerofill auto_increment not null,
	CategoryName varchar(100) not null,
	CreateAt date not null default current_date(),
	DeleteAt date default null,
	primary key (CategoryId),
	unique (CategoryName)
);

create table Product
(
	ProductId int zerofill auto_increment not null,
	ProductName varchar(300) not null,
	CreateAt date not null default current_date(),
	DeleteAt date default null,
	ImagePath varchar(200) not null,
	CategoryId smallint zerofill not null,
	primary key (ProductId),
	foreign key (CategoryId) references Category(CategoryId),
	unique (ProductName)
);

create table ProductSize
(
	ProductId int zerofill not null,
	Sign varchar(4) not null,
	Price int not null check (Price >= 0),
	VolumeML int not null check (VolumeML >= 0),
	primary key (ProductId, Sign),
	foreign key (ProductId) references Product(ProductId),
	foreign key (Sign) references Sizes(Sign)
	
);

create table ProductRecipe
(
	ProductId int zerofill not null,
	IngredientId int zerofill not null,
	IngredientQty int not null check (IngredientQty > 0),
	ProductQty int not null check (ProductQty > 0),
	primary key (ProductId, IngredientId),
	foreign key (ProductId) references Product(ProductId),
	foreign key (IngredientId) references Ingredient(IngredientId)
);

create table OrderDetail
(
	OrderId int zerofill not null,
	ProductId int zerofill not null,
	Qty int not null check (Qty > 0),
	Sizes varchar(4) not null,
	primary key (OrderId, ProductId, Sizes),
	foreign key (OrderId) references Orders(OrderId),
	foreign key (ProductId) references Product(ProductId),
	foreign key (Sizes) references Sizes(Sign)
);

create table Employee
(
	EmployeeId int zerofill auto_increment not null,
	EmployeeName varchar(200) not null,
	Phone varchar(12) not null,
	Address varchar(200) not null,
	PositionId smallint zerofill not null,
	primary key (EmployeeId),
	foreign key (PositionId) references WorkPosition(PositionId),
	unique (Phone)
);

create table Account
(
	AccountUsername varchar(100) not null,
	AccountPassword varchar(500) not null,
	EmployeeId int zerofill not null,
	RolesId smallint zerofill not null,
	primary key (AccountUsername),
	foreign key (EmployeeId) references Employee(EmployeeId),
	foreign key (RolesId) references Roles(RolesId)
);

create table PurchaseOrder
(
	PurchaseOrderId int zerofill auto_increment not null,
	Supplier varchar(100) not null,
	PurchaseOrderDate date not null default current_date(),
	TotalPrice int not null default 0 check (TotalPrice >= 0),
	EmployeeCreate int zerofill not null,
	EmployeeConfirm int zerofill default null,
	primary key (PurchaseOrderId),
	foreign key (EmployeeCreate) references Employee(EmployeeId),
	foreign key (EmployeeConfirm) references Employee(EmployeeId)
);

create table PurchaseOrderDetail
(
	PurchaseOrderId int zerofill not null,
	IngredientId int zerofill not null,
	OrderQty int not null check (OrderQty > 0),
	ReceiveQty int default null check ((ReceiveQty <= OrderQty and ReceiveQty >= 0) or ReceiveQty is null),
	primary key (PurchaseOrderId, IngredientId),
	foreign key (PurchaseOrderId) references PurchaseOrder(PurchaseOrderId),
	foreign key (IngredientId) references Ingredient(IngredientId)
);

-- Tạo Stored Procedures cho việc thiết lập múi giờ
delimiter //
create procedure set_timezone()
begin
	SET time_zone = 'Asia/Ho_Chi_Minh';
end; //
delimiter ;


-- Tạo các Stored Procedures để thêm dữ liệu
-- Thêm Roles
delimiter //
create procedure insert_roles(in name_role varchar(100))
begin
	insert into Roles(RolesName) values(name_role);
	select r.RolesId from Roles r where RolesName = name_role;
end; //
delimiter ;

-- Thêm Functional
delimiter //
create procedure insert_functional(in name_functional varchar(100))
begin
	insert into Functional(FunctionalName) values(name_functional); 
end; //
delimiter ;

-- Thêm RoleFunction
delimiter //
create procedure insert_rolefunction(in name_role varchar(100), in name_functional varchar(100))
begin
	set @id_role = (select RolesId from Roles where RolesName = name_role);
	set @id_functional = (select FunctionalId from Functional where FunctionalName = name_functional);
	insert into RoleFunction(RolesId, FunctionalId) values(@id_role, @id_functional); 
end; //
delimiter ;

-- Thêm Members
delimiter //
create procedure insert_members(in num_phone varchar(12), in name_member varchar(100))
begin
	insert into Members(PhoneNumber, FullName) values(num_phone, name_member);
end; //
delimiter ;

-- Thêm Sizes
delimiter //
create procedure insert_sizes(in sign_name varchar(4), in descriptions varchar(200))
begin
	insert into Sizes(Sign, Description) values(sign_name, descriptions);
end; //
delimiter ;

-- Thêm Ingredient
delimiter //
create procedure insert_ingredient(in name_ingredient varchar(200), in type_ingredient varchar(100), in price_ingredient int, in limit_inventory double)
begin
	insert into Ingredient(IngredientName, IngredientType, IngredientPrice, InventoryLimit)
	values(name_ingredient, type_ingredient, price_ingredient, limit_inventory);
end; //
delimiter ;

-- Thêm WorkPosition
delimiter //
create procedure insert_workposition(in name_position varchar(200), in level_position smallint)
begin
	insert into WorkPosition(PositionName, PositionLevel) values(name_position, level_position); 
end; //
delimiter ;

-- Thêm Employee
delimiter //
create procedure insert_employee(in name_emp varchar(200), in phone_emp varchar(12), in address_emp varchar(200), in position_name varchar(200))
begin
	set @id_position = (select PositionId from WorkPosition where PositionName = position_name limit 1);
	insert into Employee(EmployeeName, Phone, Address, PositionId) values(name_emp, phone_emp, address_emp, @id_position);
	select distinct EmployeeId from Employee where Phone = phone_emp limit 1;
end; //
delimiter ;

-- Thêm Category
delimiter //
create procedure insert_category(in name_category varchar(100))
begin
	insert into Category(CategoryName) values(name_category);
	select distinct CategoryId from Category where CategoryName = name_category limit 1;
end; //
delimiter ;

-- Thêm Orders
delimiter //
create procedure insert_orders(in total_price int, in num_phone varchar(12))
begin
	insert into Orders(TotalPrice, PhoneNumber) values(total_price, num_phone);
	select o.* from Orders o order by o.OrderId desc limit 1;
end; //
delimiter ;

-- Thêm Orders không có số điện thoại
delimiter //
create procedure insert_orders_without_phone(in total_price int)
begin
	insert into Orders(TotalPrice) values(total_price);
	select o.* from Orders o order by o.OrderId desc limit 1;
end; //
delimiter ;

-- Thêm Product
delimiter //
create procedure insert_product(in name_product varchar(300), in image_path varchar(200), in name_category varchar(100))
begin
    set @id_category = (select CategoryId from Category where CategoryName = name_category);
	insert into Product(ProductName, ImagePath, CategoryId) values(name_product, image_path, @id_category);
	select distinct ProductId from Product where ProductName = name_product limit 1;
end; //
delimiter ;

-- Thêm ProductSize
delimiter //
create procedure insert_productsize(in name_product varchar(300), in sign_size varchar(4), in price_product int, in volume_product int)
begin
	set @id_product = (select ProductId from Product where ProductName = name_product);
	insert into ProductSize(ProductId, Sign, Price, VolumeML) values(@id_product, sign_size, price_product, volume_product); 
end; //
delimiter ;

-- Thêm ProductRecipe
delimiter //
create procedure insert_productrecipe(in name_product varchar(300), in name_ingredient varchar(200), in qty_ingredient int, in qty_product int)
begin
	set @id_product = (select ProductId from Product where ProductName = name_product);
	set @id_ingredient = (select IngredientId from Ingredient where IngredientName = name_ingredient);
	insert into ProductRecipe(ProductId, IngredientId, IngredientQty, ProductQty) values(@id_product, @id_ingredient, qty_ingredient, qty_product);
end; //
delimiter ;

-- Thêm OrderDetail
delimiter //
create procedure insert_orderdetail(in id_order int, in name_product varchar(300), in qty_product int, in size_product varchar(4))
begin
	set @id_product = (select ProductId from Product where ProductName = name_product);
	insert into OrderDetail(OrderId, ProductId, Qty, Sizes) values(id_order, @id_product, qty_product, size_product); 
end; //
delimiter ;

-- Thêm Account
delimiter //
create procedure insert_account(in username_account varchar(100), in password_account varchar(500), in id_emp int, in name_role varchar(100))
begin
	set @id_role = (select RolesId from Roles where RolesName = name_role);
	set @sha_password = sha2(password_account,512);
	insert into Account(AccountUsername, AccountPassword, EmployeeId, RolesId) 
	values(username_account, @sha_password, id_emp, @id_role);
end; //
delimiter ;

-- Thêm PurchaseOrder
delimiter //
create procedure insert_purchaseorder(in name_supplier varchar(100), in id_emp_create int)
begin
	insert into PurchaseOrder(Supplier, EmployeeCreate) values(name_supplier, id_emp_create);
	select po.PurchaseOrderId from PurchaseOrder po order by po.PurchaseOrderId desc limit 1;
end; //
delimiter ;

-- Thêm PurchaseOrderDetail
delimiter //
create procedure insert_purchaseorderdetail(in id_purchaseorder int, in id_ingredient varchar(200), in qty_order int)
begin
	insert into PurchaseOrderDetail(PurchaseOrderId, IngredientId, OrderQty) values(id_purchaseorder, id_ingredient, qty_order);
end; //
delimiter ;

-- Tạo các Stored Procedures để lấy dữ liệu
-- Lấy dữ liệu Roles
delimiter //
create procedure select_roles()
begin
	select * from Roles;
end; //
delimiter ;

-- Lấy dữ liệu Functional
delimiter //
create procedure select_functional()
begin
	select * from Functional;
end; //
delimiter ;

-- Lấy dữ liệu RoleFunction
delimiter //
create procedure select_rolefunction()
begin
	select distinct r.RolesName, group_concat(distinct f.FunctionalName separator ';') as FunctionalName from RoleFunction rf
	inner join Roles r on r.RolesId = rf.RolesId
	inner join Functional f on f.FunctionalId = rf.FunctionalId
	group by rf.RolesId;
end; //
delimiter ;

-- Lấy dữ liệu Members
delimiter //
create procedure select_members()
begin
	select * from Members;
end; //
delimiter ;

-- Lấy dữ liệu Members bằng số điện thoại
delimiter //
create procedure select_members_with_phone(in num_phone varchar(12))
begin
	select distinct * from Members
	where PhoneNumber = num_phone;
end; //
delimiter ;

-- Lấy dữ liệu Sizes theo Sign
delimiter //
create procedure select_sizes_with_sign(in sign_name varchar(4))
begin
	select distinct * from Sizes
	where Sign = sign_name;
end; //
delimiter ;

-- Lấy dữ liệu Sizes
delimiter //
create procedure select_sizes()
begin
	select * from Sizes;
end; //
delimiter ;

-- Lấy dữ liệu Category
delimiter //
create procedure select_category()
begin
	select * from Category;
end; //
delimiter ;

-- Lấy dữ liệu Ingredient
delimiter //
create procedure select_ingredient()
begin
	select * from Ingredient;
end; //
delimiter ;

-- Lấy dữ liệu WorkPosition
delimiter //
create procedure select_workposition()
begin
	select * from WorkPosition;
end; //
delimiter ;

-- Lấy dữ liệu Product với tên sản phẩm
delimiter //
create procedure select_product_with_name(in name_product varchar(300))
begin
	select distinct * from Product
	where ProductName = name_product;
end; //
delimiter ;

-- Lấy dữ liệu Product đang kinh doanh
delimiter //
create procedure select_product_delete_is_null()
begin
	select distinct p.ProductId, p.ProductName, p.CreateAt, p.DeleteAt, p.ImagePath, c.CategoryName from Product p 
	inner join Category c on c.CategoryId = p.CategoryId
	where p.DeleteAt is null;
end; //
delimiter ;

-- Lấy dữ liệu Product ngừng kinh doanh
delimiter //
create procedure select_product_delete_not_null()
begin
	select distinct p.ProductId, p.ProductName, p.CreateAt, p.DeleteAt, p.ImagePath, c.CategoryName from Product p 
	inner join Category c on c.CategoryId = p.CategoryId
	where p.DeleteAt is not null;
end; //
delimiter ;

-- Lấy dữ liệu ProductSize
delimiter //
create procedure select_productsize()
begin
	select distinct p.ProductName, ps.Sign, ps.Price, ps.VolumeML from ProductSize ps
	inner join Product p on p.ProductId = ps.ProductId;
end; //
delimiter ;

-- Lấy dữ liệu ProductSize với mã sản phẩm
delimiter //
create procedure select_productsize_with_id(in id_product int)
begin
	select distinct p.ProductName, ps.Sign, ps.Price, ps.VolumeML from ProductSize ps
	inner join Product p on p.ProductId = ps.ProductId
	where p.ProductId = id_product;
end; //
delimiter ;

-- Lấy dữ liệu ProductRecipe
delimiter //
create procedure select_productrecipe()
begin
	select distinct p.ProductName, i.IngredientName, pr.IngredientQty, pr.ProductQty from ProductRecipe pr
	inner join Product p on p.ProductId = pr.ProductId
	inner join Ingredient i on i.IngredientId = pr.IngredientId;
end; //
delimiter ;

-- Lấy dữ liệu ProductRecipe với Mã sản phẩm - Viết theo yêu cầu của Huyền
delimiter //
create procedure select_productrecipe_with_id_product(in id_product int)
begin
	select distinct p.ProductName, i.IngredientName, pr.IngredientQty, pr.ProductQty from ProductRecipe pr
	inner join Product p on p.ProductId = pr.ProductId
	inner join Ingredient i on i.IngredientId = pr.IngredientId
	where pr.ProductId = id_product;
end; //
delimiter ;

-- Lấy dữ liệu Orders
delimiter //
create procedure select_orders()
begin
	select * from Orders;
end; //
delimiter ;

-- Lấy dữ liệu OrderDetail
delimiter //
create procedure select_orderdetail()
begin
	select distinct od.OrderId, p.ProductName, od.Qty, od.Sizes from OrderDetail od
	inner join Product p on p.ProductId = p.ProductName;
end; //
delimiter ;

-- Lấy dữ liệu OrderDetail với mã hóa đơn
delimiter //
create procedure select_orderdetail_with_orderid(in id_order int)
begin
	select distinct od.OrderId, p.ProductName, od.Qty, od.Sizes from OrderDetail od
	inner join Product p on p.ProductId = od.ProductId
	where od.OrderId = id_order;
end; //
delimiter ;

-- Lấy dữ liệu Employee bằng Tên tài khoản
delimiter //
create procedure select_employee_with_username(in user_account varchar(100))
begin
	select distinct e.*, wp.PositionName, wp.PositionLevel from Employee e
	inner join WorkPosition wp on wp.PositionId = e.PositionId
	inner join Account a on a.EmployeeId = e.EmployeeId
	where a.AccountUsername = user_account;
end; //
delimiter ;

-- Lấy dữ liệu Employee
delimiter //
create procedure select_employee()
begin
	select e.EmployeeId, e.EmployeeName, e.Phone, e.Address, wp.PositionName from Employee e
	inner join WorkPosition wp on wp.PositionId = e.PositionId;
end; //
delimiter ;

-- Lấy dữ liệu Account
delimiter //
create procedure select_account()
begin
	select distinct a.AccountUsername, a.AccountPassword, a.EmployeeId, e.EmployeeName, a.RolesId, r.RolesName from Account a
	inner join Employee e on e.EmployeeId = a.EmployeeId
	inner join Roles r on r.RolesId = a.RolesId;
end; //
delimiter ;

-- Lấy dữ liệu PurchaseOrder
delimiter //
create procedure select_purchaseorder()
begin
	select distinct po.PurchaseOrderId, po.Supplier, po.PurchaseOrderDate, po.TotalPrice, po.EmployeeCreate, po.EmployeeConfirm, ecr.EmployeeName as EmployeeNameCreate, eco.EmployeeName as EmployeeNameConfirm from PurchaseOrder po
	inner join Employee ecr on ecr.EmployeeId = po.EmployeeCreate
	left join Employee eco on eco.EmployeeId = po.EmployeeConfirm;
end; //
delimiter ;

-- Lấy dữ liệu PurchaseOrderDetail
delimiter //
create procedure select_purchaseorderdetail()
begin
	select distinct pod.PurchaseOrderId, i.IngredientName, pod.OrderQty, pod.ReceiveQty from PurchaseOrderDetail pod
	inner join PurchaseOrder po on po.PurchaseOrderId = pod.PurchaseOrderId
	inner join Ingredient i on i.IngredientId = pod.IngredientId;
end; //
delimiter ;

-- Lấy dữ liệu PurchaseOrderDetail theo Mã đơn hàng - Viết riêng cho Huyền
delimiter //
create procedure select_purchaseorderdetail_with_id(in id_purchaseorder int)
begin
	select distinct pod.PurchaseOrderId, i.IngredientName, pod.OrderQty, pod.ReceiveQty from PurchaseOrderDetail pod
	inner join PurchaseOrder po on po.PurchaseOrderId = pod.PurchaseOrderId
	inner join Ingredient i on i.IngredientId = pod.IngredientId
	where pod.PurchaseOrderId = id_purchaseorder;
end; //
delimiter ;

-- Lấy dữ liệu các nguyên liệu dưới hạn mức tồn
delimiter //
create procedure select_ingredient_store_smaller_limit()
begin
	select * from Ingredient
	where IngredientStorage < InventoryLimit;
end; //
delimiter ;

-- Lấy dữ liệu tên của Functional bằng RolesId
delimiter //
create procedure select_functionalname_with_rolesid(in id_role int)
begin
	select distinct f.FunctionalName from Functional f
	inner join RoleFunction rf on rf.FunctionalId = f.FunctionalId
	where rf.RolesId = id_role;
end; //
delimiter ;

-- Lấy dữ liệu thống kê theo ngày
delimiter //
create procedure select_statistic_day(in start_date date, in end_date date)
begin
	select o.OrderDate, count(o.OrderId) as num_of_bill, sum(o.TotalPrice) as total_price, avg(o.TotalPrice) as avg_price from Orders o
	where o.OrderDate between start_date and end_date 
	group by o.OrderDate;
end; //
delimiter ;

-- Lấy dữ liệu thống kê theo sản phẩm
delimiter //
create procedure select_statistic_product(in start_date date, in end_date date)
begin
	select product_orderdate_tmp.ProductName, sum(product_orderdate_tmp.total_product) as total_product, sum(product_orderdate_tmp.product_revenue) as product_revenue 
	from (select product_tmp.ProductName, sum(product_tmp.Qty) as total_product, sum(product_tmp.Qty*product_tmp.Price) as product_revenue from (select p.ProductName, od.Sizes, ps.Price, od.Qty, o.OrderDate from OrderDetail od
	inner join Orders o on o.OrderId = od.OrderId
	inner join Product p on p.ProductId = od.ProductId
	inner join ProductSize ps on ps.ProductId = p.ProductId) as product_tmp
	where product_tmp.OrderDate between start_date and end_date
	group by product_tmp.OrderDate, product_tmp.ProductName) as product_orderdate_tmp
	group by product_orderdate_tmp.ProductName;
end; //
delimiter ;

-- Lấy dữ liệu thống kê sản phẩm theo size
delimiter //
create procedure select_statistic_product_size(in start_date date, in end_date date)
begin
	select product_size_tmp.ProductName, product_size_tmp.Sizes, sum(product_size_tmp.total_size) as total_size, sum(product_size_tmp.size_revenue) as size_revenue 
	from (select product_tmp.ProductName, product_tmp.Sizes, sum(product_tmp.Qty) as total_size, sum(product_tmp.Qty*product_tmp.Price) as size_revenue from (select p.ProductName, od.Sizes, ps.Price, od.Qty, o.OrderDate from OrderDetail od
	inner join Orders o on o.OrderId = od.OrderId
	inner join Product p on p.ProductId = od.ProductId
	inner join ProductSize ps on ps.ProductId = p.ProductId) as product_tmp
	where product_tmp.OrderDate between start_date and end_date
	group by product_tmp.OrderDate, product_tmp.ProductName, product_tmp.Sizes) as product_size_tmp
	group by product_size_tmp.ProductName, product_size_tmp.Sizes;
end; //
delimiter ;

-- Lấy dữ liệu thống kê theo phân loại sản phẩm
delimiter //
create procedure select_statistic_category(in start_date date, in end_date date)
begin
	select category_statistic_tmp.CategoryName, sum(category_statistic_tmp.category_revenue) as category_revenue, sum(category_statistic_tmp.category_qty) as category_qty
	from (select category_tmp.OrderDate, category_tmp.CategoryName, sum(category_tmp.Qty*category_tmp.Price) as category_revenue, sum(category_tmp.Qty) as category_qty from (select c.CategoryName, p.ProductName, od.Qty, ps.Price, o.OrderDate from OrderDetail od
	inner join Product p on p.ProductId = od.ProductId
	inner join Category c on c.CategoryId = p.CategoryId
	inner join ProductSize ps on ps.ProductId = p.ProductId
	inner join Orders o on o.OrderId = od.OrderId) as category_tmp
	where category_tmp.OrderDate between start_date and end_date
	group by category_tmp.OrderDate, category_tmp.CategoryName) as category_statistic_tmp
	group by category_statistic_tmp.CategoryName;
end; //
delimiter ;

-- Tạo Stored Procedures để kiểm tra mật khẩu
delimiter //
create procedure check_user_pass(in username_account varchar(100), in password_account varchar(500))
begin
	select RolesId from Account 
	where AccountUsername = username_account and sha2(password_account,512) = AccountPassword limit 1;
end; //
delimiter ;

-- Tạo Stored Procedures để cập nhật các bảng
-- Cập nhật Roles
delimiter //
create procedure update_role_where_name(in name_role varchar(100), in new_role_name varchar(100))
begin
	update Roles
	set RolesName = new_role_name
	where RolesName = name_role;
end; //
delimiter ;

-- Cập nhật Functional
delimiter //
create procedure update_functional_where_name(in name_functional varchar(100), in new_functional_name varchar(100))
begin
	update Functional
	set FunctionalName = new_functional_name
	where FunctionalName = name_functional;
end; //
delimiter ;

-- Cập nhật cộng điểm cho Members
delimiter //
create procedure update_add_point_member(in num_phone varchar(12), in add_point int)
begin
	update Members
	set Points = Points + add_point
	where PhoneNumber = num_phone;
end; //
delimiter ;

-- Cập nhật trừ điểm cho Members
delimiter //
create procedure update_sub_point_member(in num_phone varchar(12), in sub_point int)
begin
	update Members
	set Points = Points - sub_point
	where PhoneNumber = num_phone;
end; //
delimiter ;

-- Cập nhật một vài cột Ingredient
delimiter //
create procedure update_ingredient(in id_ingredient int, in name_ingredient varchar(200), in type_ingredient varchar(200), in price_ingredient int, in limit_ingredient int)
begin
	update Ingredient
	set IngredientName = name_ingredient, IngredientType = type_ingredient, IngredientPrice = price_ingredient, InventoryLimit = limit_ingredient
	where IngredientId = id_ingredient;
end; //
delimiter ;

-- Cập nhật Ingredient
delimiter //
create procedure update_ingredient_where_name(in name_column varchar(50), in value_update varchar(200), in name_ingredient varchar(200))
begin
	if name_column = "IngredientName" then
		update Ingredient
		set IngredientName = value_update
		where IngredientName = name_ingredient;
	elseif name_column = "IngredientType" then
		update Ingredient
		set IngredientType = value_update
		where IngredientName = name_ingredient;
	elseif name_column = "IngredientPrice" then
		set value_update = cast(value_update as int);
		update Ingredient
		set IngredientPrice = value_update
		where IngredientName = name_ingredient;
	elseif name_column = "IngredientStorage" then
		set value_update = cast(value_update as double);
		update Ingredient
		set IngredientStorage = value_update
		where IngredientName = name_ingredient;
	elseif name_column = "InventoryLimit" then
		set value_update = cast(value_update as double);
		update Ingredient
		set InventoryLimit = value_update
		where IngredientName = name_ingredient;
	elseif name_column = "CreateAt" then
		set value_update = cast(value_update as date);
		update Ingredient
		set CreateAt = value_update
		where IngredientName = name_ingredient;
	elseif name_column = "DeleteAt" then
		set value_update = cast(value_update as date);
		update Ingredient
		set DeleteAt = value_update
		where IngredientName = name_ingredient;
	else
		signal sqlstate '45100' SET MESSAGE_TEXT = 'Wrong column name or data in procedure';
	end if;
end; //
delimiter ;

-- Cập nhật một số thuộc tính của Product
delimiter //
create procedure update_product(in id_product int, in name_product varchar(300), in image_path varchar(200), in name_category varchar(100))
begin
    set @id_category = (select distinct CategoryId from Category where CategoryName = name_category limit 1);
	update Product
	set ProductName = name_product, ImagePath = image_path, CategoryId = @id_category
	where ProductId = id_product;
end; //
delimiter ;

-- Cập nhật Product
delimiter //
create procedure update_product_where_name(in name_column varchar(50), in value_update varchar(200), in name_product varchar(200))
begin
	if name_column = "ProductName" then
		update Product
		set ProductName = value_update
		where ProductName = name_product;
	elseif name_column = "ImagePath" then
		update Product
		set ImagePath = value_update
		where ProductName = name_product;
	elseif name_column = "CreateAt" then
		set value_update = cast(value_update as date);
		update Product
		set CreateAt = value_update
		where ProductName = name_product;
	elseif name_column = "DeleteAt" then
		set value_update = cast(value_update as date);
		update Product
		set DeleteAt = value_update
		where ProductName = name_product;
	else
		signal sqlstate '45100' SET MESSAGE_TEXT = 'Wrong column name or data in procedure';
	end if;
end; //
delimiter ;

-- Cập nhật Account (Password và RolesId)
delimiter //
create procedure update_password_rolesid_account(in user_account varchar(100), in password_account varchar(500), in roles_name varchar(100))
begin
	update Account
	set AccountPassword = password_account, RolesId = (select distinct RolesId from Roles where RolesName = roles_name limit 1)
	where AccountUsername = user_account;
end; //
delimiter ;

-- Cập nhật Account
delimiter //
create procedure update_account_where_username(in name_column varchar(50), in value_update varchar(200), in username_account varchar(100))
begin
	if name_column = "AccountUsername" then
		update Account
		set AccountUsername = value_update
		where AccountUsername = username_account;
	elseif name_column = "AccountPassword" then
		update Account
		set AccountPassword = sha2(value_update,512)
		where AccountUsername = username_account;
	elseif name_column = "RolesId" then
		set value_update = cast(value_update as int);
		update Account
		set RolesId = value_update
		where AccountUsername = username_account;
	else
		signal sqlstate '45100' SET MESSAGE_TEXT = 'Wrong column name or data in procedure';
	end if;
end; //
delimiter ;

-- Cập nhật ProductRecipe
delimiter //
create procedure update_productrecipe(in name_column varchar(50), in value_update int, in id_product int, in id_ingredient int)
begin
	if name_column = "IngredientId" && id_product is not null && id_ingredient is not null then
		update ProductRecipe
		set IngredientId = value_update
		where ProductId = id_product and IngredientId = id_ingredient;
	elseif name_column = "IngredientQty" && id_product is not null && id_ingredient is not null then
		update ProductRecipe
		set IngredientQty = value_update
		where ProductId = id_product and IngredientId = id_ingredient;
	elseif name_column = "ProductQty" && id_product is not null && id_ingredient is null then
		update ProductRecipe
		set ProductQty = value_update
		where ProductId = id_product;
	else
		signal sqlstate '45100' SET MESSAGE_TEXT = 'Wrong column name or data in procedure';
	end if;
end; //
delimiter ;

-- Cập nhật Category
delimiter //
create procedure update_category(in id_category int, in name_category varchar(100))
begin
	update Category
	set CategoryName = name_category
	where CategoryId = id_category;
end; //
delimiter ;

-- Cập nhật ngày xóa Category (cập nhật ngày xóa bằng null để bán lại)
delimiter //
create procedure update_category_deleteat_to_null(in id_category int)
begin
	update Category
	set DeleteAt = Null
	where CategoryId = id_category;
end; //
delimiter ;

-- Cập nhật EmployeeConfirm PurchaseOrder
delimiter //
create procedure update_employeeconfirm_purchaseorder(in id_purchaseorder int, in id_employeeconfirm int)
begin
	update PurchaseOrder
	set EmployeeConfirm = id_employeeconfirm 
	where PurchaseOrderId = id_purchaseorder;
end; //
delimiter ;

-- Cập nhật ReceiveQty PurchaseOrderDetail
delimiter //
create procedure update_receiveqty_purchaseorderdetail(in id_purchaseorder int, in id_ingredient int, in receiveqty int)
begin
	update PurchaseOrderDetail
	set ReceiveQty = receiveqty
	where PurchaseOrderId = id_purchaseorder and IngredientId = id_ingredient;
end; 
delimiter ;

-- Cập nhật ProductSize
delimiter //
create procedure update_productsize(in id_product int, in old_sign_product varchar(4), in new_sign_product varchar(4), in price_product int, in volume_product int)
begin
	update ProductSize
	set Sign = new_sign_product, Price = price_product, VolumeML = volume_product
	where ProductId = id_product and Sign = old_sign_product;
end; //
delimiter ;

-- Cập nhật ProductSize với tên sản phẩm
delimiter //
create procedure update_productsize_with_name(in name_product varchar(300), in old_sign_product varchar(4), in price_product int, in volume_product int)
begin
	update ProductSize
	set Price = price_product, VolumeML = volume_product
    where Sign = old_sign_product and ProductId = (select distinct ProductId from Product where ProductName = name_product limit 1);
end; //
delimiter ;

-- Cập nhật Employee
delimiter //
create procedure update_employee(in id_employee int, in name_employee varchar(200), in phone_employee varchar(12), in address_employee varchar(200), in name_position varchar(200))
begin
	set @id_position = (select PositionId from WorkPosition where PositionName = name_position limit 1);
	update Employee
	set EmployeeId = id_employee, EmployeeName = name_employee, Phone = phone_employee, Address = address_employee, PositionId = @id_position 
	where EmployeeId = id_employee;
end; //
delimiter ;

-- Cập nhật Members
delimiter //
create procedure update_members(in old_phone_num varchar(12), in fullname_member varchar(100), in point_member int)
begin
	update Members
	set FullName = fullname_member, Points = point_member
	where PhoneNumber = old_phone_num;
end; //
delimiter ;

-- Cập nhật Sizes
delimiter //
create procedure update_sizes(in old_sign varchar(4), in new_sign varchar(4), in description_sign varchar(200))
begin
	update Sizes
	set Sign = new_sign, Description = description_sign
	where Sign = old_sign;
end; //
delimiter ;

-- Tạo Stored Procedures để xóa dữ liệu các bảng
-- Xóa dữ liệu RoleFunction
delimiter //
create procedure delete_rolefunction_with_id(in id_role int, id_functional int)
begin
	delete from RoleFunction
	where RolesId = id_role and FunctionalId = id_functional;
end; //
delimiter ;

-- Xóa dữ liệu ProductRecipe
delimiter //
create procedure delete_productrecipe_with_id(in id_product int, in id_ingredient int)
begin
	delete from ProductRecipe
	where ProductId = id_product and IngredientId = id_ingredient;
end; //
delimiter ;

-- Tạo các Triggers
-- Trigger ngày xóa không được nhỏ hơn ngày tạo trên bảng Ingredient
delimiter //
create trigger trigger_delete_date_ingredient
before update on Ingredient for each row
begin
	if new.DeleteAt >= old.CreateAt || new.DeleteAt is null then
		set new.DeleteAt = new.DeleteAt; 
	else
		signal sqlstate '45000' SET MESSAGE_TEXT = 'Date create must not greater than date delete';
	end if;
end; //
delimiter ;

-- Trigger ngày xóa không được nhỏ hơn ngày tạo trên bảng Category
delimiter //
create trigger trigger_delete_date_category
before update on Category for each row
begin
	if new.DeleteAt >= old.CreateAt || new.DeleteAt is null then
		set new.DeleteAt = new.DeleteAt; 
	else
		signal sqlstate '45000' SET MESSAGE_TEXT = 'Date create must not greater than date delete';
	end if;
end; //
delimiter ;

-- Trigger ngày xóa không được nhỏ hơn ngày tạo trên bảng Product
delimiter //
create trigger trigger_delete_date_product
before update on Product for each row
begin
	if new.DeleteAt >= old.CreateAt || new.DeleteAt is null then
		set new.DeleteAt = new.DeleteAt; 
	else
		signal sqlstate '45000' SET MESSAGE_TEXT = 'Date create must not greater than date delete';
	end if;
end; //
delimiter ;

-- Trigger ngày tạo hóa đơn không được bé hơn hoặc lớn hơn ngày hôm nay
delimiter //
create trigger trigger_insert_orderdate
before insert on Orders for each row
begin
	if new.OrderDate = current_date() then
		set new.OrderDate = new.OrderDate;
	else
		signal sqlstate '45001' SET MESSAGE_TEXT = 'Date create order must smaller or equal than current date';
	end if;
end; //
delimiter ;

-- Trigger kiểm tra số điện thoại của Employee trước khi thêm nhân viên mới
delimiter //
create trigger trigger_insert_employeephone
before insert on Employee for each row
begin
	if (new.Phone regexp '^((\\+84)[3|5|7|8|9])+([0-9]{8})$' && length(new.Phone) = 12) then
		set new.Phone = concat('0',substring(new.Phone,4));
	elseif (new.Phone regexp '^(0[3|5|7|8|9])+([0-9]{8})$' && length(new.Phone) = 10) then
	    set new.Phone = new.Phone;
	else
		signal sqlstate '45200' SET MESSAGE_TEXT = 'Wrong type phone number';
	end if;
end; //
delimiter ;

-- Trigger kiểm tra số điện thoại của Employee trước khi cập nhật nhân viên mới
delimiter //
create trigger trigger_update_employeephone
before update on Employee for each row
begin
	if (new.Phone regexp '^((\\+84)[3|5|7|8|9])+([0-9]{8})$' && length(new.Phone) = 12) then
		set new.Phone = concat('0',substring(new.Phone,4));
	elseif (new.Phone regexp '^(0[3|5|7|8|9])+([0-9]{8})$' && length(new.Phone) = 10) then
	    set new.Phone = new.Phone;
	else
		signal sqlstate '45200' SET MESSAGE_TEXT = 'Wrong type phone number';
	end if;
end; //
delimiter ;

-- Trigger kiểm tra số điện thoại của Members trước khi thêm thành viên viên mới
delimiter //
create trigger trigger_insert_memberphone
before insert on Members for each row
begin
	if (new.PhoneNumber regexp '^((\\+84)[3|5|7|8|9])+([0-9]{8})$' && length(new.PhoneNumber) = 12) then
		set new.PhoneNumber = concat('0',substring(new.PhoneNumber,4));
	elseif (new.PhoneNumber regexp '^(0[3|5|7|8|9])+([0-9]{8})$' && length(new.PhoneNumber) = 10) then
	    set new.PhoneNumber = new.PhoneNumber;
	else
		signal sqlstate '45200' SET MESSAGE_TEXT = 'Wrong type phone number';
	end if;
end; //
delimiter ;

-- Trigger kiểm tra số điện thoại của Members trước khi cập nhật thành viên viên mới
delimiter //
create trigger trigger_update_memberphone
before update on Members for each row
begin
	if (new.PhoneNumber regexp '^((\\+84)[3|5|7|8|9])+([0-9]{8})$' && length(new.PhoneNumber) = 12) then
		set new.PhoneNumber = concat('0',substring(new.PhoneNumber,4));
	elseif (new.PhoneNumber regexp '^(0[3|5|7|8|9])+([0-9]{8})$' && length(new.PhoneNumber) = 10) then
	    set new.PhoneNumber = new.PhoneNumber;
	else
		signal sqlstate '45200' SET MESSAGE_TEXT = 'Wrong type phone number';
	end if;
end; //
delimiter ;

-- Trigger tự động tăng tổng giá (TotalPrice) của đơn đặt hàng khi cập nhật chi tiết đơn đặt hàng
delimiter //
create trigger trigger_update_totalprice_purchaseorder
before update on PurchaseOrderDetail for each row
begin
	set @name_ingredient = (select distinct i2.IngredientName from Ingredient i2 where i2.IngredientId = new.IngredientId);
	if old.ReceiveQty is null and new.ReceiveQty is not null then
		update PurchaseOrder
		set TotalPrice = TotalPrice + case when @name_ingredient not in ("Bánh mì","Trứng") then (select distinct round((i.IngredientPrice/1000)*new.ReceiveQty,0) as total_price from Ingredient i where i.IngredientId = new.IngredientId limit 1)
		    when @name_ingredient in ("Bánh mì","Trứng") then (select distinct round(i.IngredientPrice*new.ReceiveQty,0) as total_price from Ingredient i where i.IngredientId = new.IngredientId limit 1) end
		where PurchaseOrderId = new.PurchaseOrderId;
	elseif old.ReceiveQty is not null and new.ReceiveQty is not null then
		update PurchaseOrder
		set TotalPrice = TotalPrice + case when @name_ingredient not in ("Bánh mì","Trứng") then (select distinct round((i.IngredientPrice/1000)*(new.ReceiveQty-old.ReceiveQty),0) as total_price from Ingredient i where i.IngredientId = new.IngredientId limit 1)
		    when @name_ingredient in ("Bánh mì","Trứng") then (select distinct round(i.IngredientPrice*(new.ReceiveQty-old.ReceiveQty),0) as total_price from Ingredient i where i.IngredientId = new.IngredientId limit 1) end
		where PurchaseOrderId = new.PurchaseOrderId;
	elseif old.ReceiveQty is not null and new.ReceiveQty is null then 
		update PurchaseOrder
		set TotalPrice = TotalPrice - case when @name_ingredient not in ("Bánh mì","Trứng") then (select distinct round((i.IngredientPrice/1000)*old.ReceiveQty,0) as total_price from Ingredient i where i.IngredientId = old.IngredientId limit 1)
		    when @name_ingredient in ("Bánh mì","Trứng") then (select distinct round(i.IngredientPrice*old.ReceiveQty,0) as total_price from Ingredient i where i.IngredientId = old.IngredientId limit 1) end
		where PurchaseOrderId = old.PurchaseOrderId;
	end if;
end; //
delimiter ;

-- Trigger tự động tăng 1 điểm cho Members khi tổng giá trong Orders bằng 100,000
delimiter //
create trigger trigger_update_points_members
after insert on Orders for each row
begin
	set @total_price = (select sum(o.TotalPrice) from Orders o where o.PhoneNumber is not null and new.PhoneNumber = o.PhoneNumber group by o.PhoneNumber);
	update Members
	set Points = Points + floor(((@total_price) - Points*100000)/100000)
	where new.PhoneNumber is not null and new.PhoneNumber = PhoneNumber;
end; //
delimiter ;

-- Trigger tự động trừ điểm cho các thành viên đã 2 năm không mua hàng
delimiter //
create trigger trigger_update_point_to_zero
before insert on Orders for each row
begin
	update Members
	set Points = 0
	where PhoneNumber in (select max_order_tmp.PhoneNumber from (select order_tmp.PhoneNumber, order_tmp.FullName, order_tmp.Points, max(order_tmp.OrderDate)
						from (select distinct m.*, o.OrderDate from Members m
						inner join Orders o on o.PhoneNumber = m.PhoneNumber) as order_tmp
						group by order_tmp.PhoneNumber having timestampdiff(year, max(order_tmp.OrderDate), current_date()) = 2) as max_order_tmp);
end; //
delimiter ;

-- Trigger kiểm tra điểm xem có được trừ hay không
delimiter //
create trigger trigger_update_points_members_when_use_points
before update on Members for each row
begin
	set @total_price = (select sum(o.TotalPrice) from Orders o where o.PhoneNumber is not null and new.PhoneNumber = o.PhoneNumber);
	if new.Points > floor(@total_price/100000) then
		signal sqlstate '45400' SET MESSAGE_TEXT = 'Wrong points of members. Points not equal to total price order';
	elseif mod(old.Points - new.Points,10) = 0 then
		set new.Points = new.Points;
	elseif new.Points >= old.Points and new.Points >= 0 and (mod(old.Points - new.Points,10) <> 0 or mod(old.Points - new.Points,10) = 0) then
		set new.Points = new.Points;
	elseif new.Points <= old.Points and mod(old.Points - new.Points,10) = 0 and new.Points >= 0 then
	    set new.Points = new.Points;
	else
		signal sqlstate '45300' SET MESSAGE_TEXT = 'Wrong points of members. Point must be like 0,10,20,30';
	end if;
end; //
delimiter ;