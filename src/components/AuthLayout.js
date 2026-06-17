
const AuthLayout = ({ children }) => {
  return (
    <div
      style={{
        height: "100vh",
        backgroundColor: "#f5f7fa",
        display: "flex",
        justifyContent: "center",
        alignItems: "center"
      }}
    >
      <div
        style={{
          width: "360px",
          backgroundColor: "#ffffff",
          padding: "40px",
          borderRadius: "20px",
          boxShadow: "0 10px 30px rgba(0,0,0,0.1)"
        }}
      >
        {children}
      </div>
    </div>
  );
};

export default AuthLayout;